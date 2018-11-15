package com.my.hello.service.impl;

import com.my.hello.dataobject.OrderDetail;
import com.my.hello.dataobject.OrderMaster;
import com.my.hello.dataobject.ProductInfo;
import com.my.hello.dto.CartDTO;
import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.repository.OrderDetailRepository;
import com.my.hello.repository.OrderMasterRepository;
import com.my.hello.service.OrderMasterService;
import com.my.hello.service.ProductCategoryService;
import com.my.hello.service.ProductInfoService;
import com.my.hello.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
           ProductInfo productInfo = productInfoService.fidnOne(orderDetail.getProductId());
           if(productInfo == null){
                    /** 提示 商品不存在,没办法下订单*/
                    throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
            //2、得到一件商品的总价
            bigDecimal=orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(bigDecimal);
            //3、订单入库
            orderDetail.setDetailId(KeyUtil.genUnique());
            orderDetail.setOrderId(random);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            //CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOS.add(cartDTO);

        }
        //3、写入订单数据库 OrderMaster OrderDetail
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(random);
        orderMaster.setOrderAmount(bigDecimal);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        repository.save(orderMaster);
        //4、如果下单成功,扣库存
        List<CartDTO> cartDTOList =
            orderDTO.getOrderDetailList().stream().map(e ->
                 new CartDTO(e.getProductId(),e.getProductQuantity()) ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        return orderDTO;
    }

    /** 查询单个*/
    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    /** 查询订单列表*/
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    /** 取消订单*/
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    /** 完结订单*/
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }


    /** 支付订单*/
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
