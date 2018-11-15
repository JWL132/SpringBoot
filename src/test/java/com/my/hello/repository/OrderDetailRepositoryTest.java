package com.my.hello.repository;

import com.my.hello.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    /** 根据订单id来拿出具体内容*/
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails =repository.findByOrderId("11111111");
        Assert.assertNotEquals(0,orderDetails.size());
    }

    /** 保存操作*/
    @Test
    public  void  save(){

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234657810");
        orderDetail.setOrderId("11111112");
        orderDetail.setProductId("11111112");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(3);
        orderDetail.setProductIcon("http://xxxx.jpg");
        OrderDetail orderDetail1 = repository.save(orderDetail);
    }
}