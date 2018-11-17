package com.my.hello.controller;

import com.my.hello.VO.ResultVO;
import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.form.OrderForm;
import com.my.hello.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;
    //创建订单
    public ResultVO<Map<String,String>>create(@Validated OrderForm orderForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            log.error("创建订单,参数不正确,orderForm={}",orderForm);
            throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

       // OrderDTO orderDTO =
        return  null;
    }

    //订单列表

    //订单详情

    //取消订单
}
