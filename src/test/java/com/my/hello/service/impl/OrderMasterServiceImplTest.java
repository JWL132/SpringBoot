package com.my.hello.service.impl;

import com.my.hello.dataobject.OrderDetail;
import com.my.hello.dataobject.OrderMaster;
import com.my.hello.dto.OrderDTO;
import com.my.hello.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private  OrderMasterServiceImpl orderMasterService;

    private  String BUYEROPENID="110110";
    /** 创建订单*/
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("慕课网");
        orderDTO.setBuyerName("廖师兄");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYEROPENID);

        /** 购物车 */
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123457");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);


        OrderDTO orderDTO1 =orderMasterService.create(orderDTO);
        log.info("创建新订单",orderDTO1);
        Assert.assertNotNull(orderDTO1);
    }

    /** 查询单个*/
    @Test
    public void findOne() {
       OrderDTO orderDTO = orderMasterService.findOne("123456");
       log.info("查询单个订单详细信息情况",orderDTO);
       Assert.assertEquals("123456",orderDTO.getOrderId());
    }

    /** 查询订单列表*/
    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,1);
       Page<OrderDTO>orderDTOPage= orderMasterService.findList("123456",pageRequest);
      //Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    /** 取消订单*/
    @Test
    public void cancel() {

        OrderDTO orderDTO = orderMasterService.findOne("1542338877870703936");
        OrderDTO orderDTO1 = orderMasterService.cancel(orderDTO);
       // Assert.assertEquals(OrderStatusEnum.CANCEL.getCode());
    }

    /** 完结订单*/
    @Test
    public void finish() {
    }

    /** 支付订单*/
    @Test
    public void paid() {
    }
}