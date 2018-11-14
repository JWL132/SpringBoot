package com.my.hello.service.impl;

import com.my.hello.dataobject.ProductCategory;
import com.my.hello.repository.ProductCategoryRepository;
import com.my.hello.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCategortServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository  repository;

    /** 查询一条记录 */
    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    /** 查询全部信息*/
    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    /** 根据类目id查询全部信息*/
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypelist) {
        return repository.findByCategoryTypeIn(categoryTypelist);
    }

    /** 保存操作 */
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }


}
