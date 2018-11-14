package com.my.hello.repository;

import com.my.hello.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /** 根据商品的状态去查 */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
