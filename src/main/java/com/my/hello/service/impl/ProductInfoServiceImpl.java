package com.my.hello.service.impl;

import com.my.hello.dataobject.ProductInfo;
import com.my.hello.enums.ProductStatusEnum;
import com.my.hello.repository.ProductInfoRepository;
import com.my.hello.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService{
    @Autowired
    private ProductInfoRepository repository;

    /** 根据商品的ID去查*/
    @Override
    public ProductInfo fidnOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    /** 查询所有在架商品*/
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /** 管理员分页查询*/
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /** 保存操作*/
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
