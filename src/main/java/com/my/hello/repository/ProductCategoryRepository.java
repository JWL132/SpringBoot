package com.my.hello.repository;

import com.my.hello.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository <ProductCategory, Integer>{

    /** 根据类目编号去查 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypelist);



}
