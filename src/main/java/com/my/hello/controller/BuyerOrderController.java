package com.my.hello.controller;

import com.my.hello.VO.ResultVO;
import com.my.hello.converter.OrderFormOrderDTOConverter;
import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.form.OrderForm;
import com.my.hello.service.BuyerService;
import com.my.hello.service.OrderMasterService;
import com.my.hello.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PatchMapping("/create")
    public ResultVO<Map<String,String>>create(@Validated OrderForm orderForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            log.error("创建订单,参数不正确,orderForm={}",orderForm);
            throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

       OrderDTO orderDTO = OrderFormOrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("购物车不能为空");
            throw  new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO orderDTO1 = orderMasterService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderDTO1.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @PatchMapping("/list")
    public  ResultVO<List<Order>> list(@RequestParam("openid") String openid,
                                       @RequestParam(value = "page",defaultValue = "0" ) Integer page,
                                       @RequestParam(value ="size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("查询订单列表,openid为空");
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage =orderMasterService.findList(openid,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }


    //订单详情
    @GetMapping("/detail")
    public  ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                      @RequestParam("orderId") String orderId){
        //TODO 不安全的做法，待改进
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);

        return  ResultVOUtil.success(orderDTO);
    }

    //取消订单/
    @PostMapping("/cancel")
    public  ResultVO cancel(@RequestParam("openid") String openid,
                      @RequestParam("orderId") String orderId){

        buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success();
    }
}
///