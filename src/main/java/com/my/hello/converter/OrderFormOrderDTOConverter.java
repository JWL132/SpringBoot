package com.my.hello.converter;

import com.google.gson.Gson;
import com.my.hello.dto.OrderDTO;
import com.my.hello.form.OrderForm;

public class OrderFormOrderDTOConverter {

    public  static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        //orderDTO.setOrderDetailList();
        return  orderDTO;
    }
}
