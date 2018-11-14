package com.my.hello.service;

import com.my.hello.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {


    /** 根据id去查询 */
    ProductCategory findOne(Integer categoryId);

    /** 查询全部信息*/
    List<ProductCategory> findAll();

    /** 根据类目id查询全部信息*/
    List<ProductCategory>findByCategoryTypeIn(List<Integer> categoryTypelist);


    /** 保存操作 */
    ProductCategory save(ProductCategory productCategory);



}
