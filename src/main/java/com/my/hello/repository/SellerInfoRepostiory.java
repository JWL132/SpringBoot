package com.my.hello.repository;

import com.my.hello.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepostiory  extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openod);
}
