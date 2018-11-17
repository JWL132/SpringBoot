package com.my.hello.service.impl;

import com.my.hello.converter.OrderMasterOrderDTOConverter;
import com.my.hello.dataobject.OrderDetail;
import com.my.hello.dataobject.OrderMaster;
import com.my.hello.dataobject.ProductInfo;
import com.my.hello.dto.CartDTO;
import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.OrderStatusEnum;
import com.my.hello.enums.PayStatusEnum;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.repository.OrderDetailRepository;
import com.my.hello.repository.OrderMasterRepository;
import com.my.hello.service.OrderMasterService;
import com.my.hello.service.ProductCategoryService;
import com.my.hello.service.ProductInfoService;
import com.my.hello.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository repository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductInfoService productInfoService;

    /** 创建订单*/
    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        /** */
       // List<CartDTO> cartDTOS = new ArrayList<>();

        /** */
        String random= KeyUtil.genUnique();

        /** 定义一个用于接收总价的变量*/
        BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);

        //1、需要查询商品的数量、价格、
        /**
         * 逻辑:先把OrderDetail里面的数据查出来,拿出来订单id
         *     再根据ProductInfo表的订单id查询订单是否存在,
         */
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
           ProductInfo productInfo = productInfoService.fidnOne(orderDetail.getProductId());
           if(productInfo == null){
                    /** 提示 商品不存在,没办法下订单*/
                    throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
            //2、得到一件商品的总价
            bigDecimal=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(bigDecimal);
            //3、订单入库
            orderDetail.setDetailId(KeyUtil.genUnique()); //OrderDetail表的 detailId是随机生成,这里放的是一个随机数
            orderDetail.setOrderId(random);  //OrderDetail表的 OrderId是随机生成,这里放的是一个随机数
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            //CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOS.add(cartDTO);

        }
        //3、写入订单数据库 OrderMaster OrderDetail
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(random); //OrderMaster表的 OrderId是随机生成,这里放的是一个随机数
        orderMaster.setOrderAmount(bigDecimal);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode()); //
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        repository.save(orderMaster);
        //4、如果下单成功,扣库存
        List<CartDTO> cartDTOList =
            orderDTO.getOrderDetailList().stream().map(e ->
                 new CartDTO(e.getProductId(),e.getProductQuantity()) ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    /** 查询单个*/
    @Override
    public OrderDTO findOne(String orderId) {

        /** 先查询是否存在该订单*/
        OrderMaster orderMaster = repository.findById(orderId).orElse(null);
        if (orderMaster == null){
            /** 提示 订单不存在,无法查到该订单*/
            throw  new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
       List<OrderDetail>orderDetailList =  orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList == null){
            /** 提示 订单详情不存在,无法查到该订单详细情况*/
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /** 查询订单列表*/
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
       Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid(buyerOpenid,pageable);
       List<OrderDTO> orderDTOList = OrderMasterOrderDTOConverter.convert(orderMasterPage.getContent());
       //Page<OrderDTO> orderDTOPage =
        return  new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

    }

    /** 取消订单*/
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        /** 先判断订单状态 是否可以取消*/
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("订单状态不正确,请先完结订单，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERRROR);
        }
        /** 修改订单状态 */
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = repository.save(orderMaster);
        if (orderMaster1 == null){
            log.info("取消订单失败,orderMaster={}",orderMaster);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /** 返回库存*/
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("该订单详情为空,orderDTO={}",orderDTO);
            throw  new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> orderDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        /** 如果用户取消订单,那么所下单的数量也将返回 回到库存*/
        productInfoService.increaseStock(orderDTOList);
        /** 如果已支付,退款的情况下*/
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            //TODO
        }
        return orderDTO;
    }

    /** 完结订单*/
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        /** 先判断订单状态 是否可以取消*/
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("订单状态不正确,请先完结订单，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERRROR);
        }
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            log.info("您的订单未支付,请在支付后完结订单,orderId={},orderPayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_DETAIL_FINSH);
        }
        /** 修改订单状态 */
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = repository.save(orderMaster);
        if (orderMaster1 == null){
            log.info("完结订单失败,orderMaster={}",orderMaster);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }


    /** 支付订单*/
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        /** 先判断订单状态 是否可以取消*/
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("订单支付成功，请您完结订单，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERRROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.info("您的订单已支付成功,请勿再次操作,orderId={},orderPayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_DETAIL_FINSH1);
        }
        //修改支付状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = repository.save(orderMaster);
        if (orderMaster1 == null){
            log.info("订单支付失败,orderMaster={}",orderMaster);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
