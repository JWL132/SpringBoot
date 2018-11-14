package com.my.hello.repository;

import com.my.hello.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /** 按照买家openID来查某个人详细订单信息*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}