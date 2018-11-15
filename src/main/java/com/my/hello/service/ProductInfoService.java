package com.my.hello.service;

import com.my.hello.dataobject.ProductInfo;
import com.my.hello.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    /** 根据商品的ID去查*/
    ProductInfo fidnOne(String productId);

    /** 查询所有在架商品*/
    List<ProductInfo>findUpAll();

    /** 管理员分页查询*/
    Page<ProductInfo>findAll(Pageable pageable);

    /** 保存操作*/
    ProductInfo save(ProductInfo productInfo);

    /** 加库存*/
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存*/
    void decreaseStock(List<CartDTO> cartDTOList);
}
