package com.my.hello.repository;

import com.my.hello.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private  OrderMasterRepository repository;

    /** 按照买家openID来查某个人详细订单信息*/
    @Test
    public void findByBuyerOpenid() {
    }

    /** 保存操作*/
    @Test
    public  void  save(){

        OrderMaster orderMaster = new OrderMaster();
//        orderMaster.setOrderId();
//        orderMaster.setBuyerName();
//        orderMaster.setBuyerPhone();
//        orderMaster.setBuyerAddress();
//        orderMaster.getBuyerOpenid();
//        orderMaster.setOrderAmount();
//        orderMaster.setOrderStatus();
//        orderMaster.setPayStatus();
        OrderMaster orderMaster1 = repository.save(orderMaster);
    }
}