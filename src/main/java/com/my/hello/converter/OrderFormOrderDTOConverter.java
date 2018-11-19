package com.my.hello.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.hello.dataobject.OrderDetail;
import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderFormOrderDTOConverter {

    public  static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail>orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        }catch (Exception e){

            log.error("对象转换错误,String={}",orderForm.getItems());
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return  orderDTO;
    }
}
