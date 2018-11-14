package com.my.hello.service.impl;

import com.my.hello.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategortServiceImplTest {

    @Autowired
    private ProductCategortServiceImpl productCategortService;

    @Test
    public void findOne() {
        ProductCategory productCategory = productCategortService.findOne(1);
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategories = productCategortService.findAll();
    }

    /** 根据类目id查询全部信息*/
    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory>productCategories = productCategortService.findByCategoryTypeIn(Arrays.asList(1));
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("男生专享", 10);
        ProductCategory productCategory1 = productCategortService.save(productCategory);

    }
}