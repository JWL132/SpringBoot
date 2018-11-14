package com.my.hello.repository;

import com.my.hello.dataobject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    /** 根据订单id来拿出具体内容*/
    @Test
    public void findByOrderId() {
    }

    /** 保存操作*/
    @Test
    public  void  save(){

        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setDetailId();
//        orderDetail.setOrderId();
//        orderDetail.setProductId();
//        orderDetail.setProductName();
//        orderDetail.setProductPrice();
//        orderDetail.setProductQuantity();
//        orderDetail.setProductIcon();
        OrderDetail orderDetail1 = new OrderDetail();
    }
}