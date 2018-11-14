package com.my.hello.repository;

import com.my.hello.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String > {

    /** 根据订单id来拿出具体内容*/
    List<OrderDetail>findByOrderId(String orderId);
}
