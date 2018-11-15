package com.my.hello.service.impl;

import com.my.hello.dataobject.ProductInfo;
import com.my.hello.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl repository;

    /** 根据商品的ID去查*/
    @Test
    public void fidnOne() {
       ProductInfo productInfolist = repository.fidnOne("123455");
       Assert.assertNotNull(productInfolist);

    }

    /** 查询所有在架商品*/
    @Test
    public void findUpAll() {
       List<ProductInfo>productInfoList = repository.findUpAll();
        Assert.assertNotNull(productInfoList);
    }

    /** 管理员分页查询*/
    @Test
    public void findAll() {
//        PageRequest pageRequest = new PageRequest(0,2);
//        Page<ProductInfo> productInfoList = repository.findAll(pageRequest);
//        //System.out.println(productInfoList.getTotalElements());
//        /** 不等于0 */
//        Assert.assertNotEquals(0,productInfoList.getTotalElements());//
    }

    /** 保存操作*/
    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo productInfo1 = repository.save(productInfo);
        Assert.assertNotNull(productInfo1);
    }
}