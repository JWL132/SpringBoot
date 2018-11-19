package com.my.hello.service.impl;

import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.service.BuyerService;
import com.my.hello.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceimpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null){
            log.error("查询不到该订单,orderId={}",orderId);
            throw  new SellException(ResultEnum.ORDER_NOT_EXIT);
        }

        return orderMasterService.cancel(orderDTO);
    }

    private  OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if (orderDTO == null){
            return  null;
        }
        /** 判断是否自己的订单*/
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("订单的openid不一致，openid={}",openid);
            throw  new SellException(ResultEnum.ORDER_OWWER_ERROR);
        }
        return  orderDTO;
    }
}
