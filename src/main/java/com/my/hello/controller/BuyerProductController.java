package com.my.hello.controller;

import com.my.hello.VO.ProductInfoVO;
import com.my.hello.VO.ProductVO;
import com.my.hello.VO.ResultVO;
import com.my.hello.dataobject.ProductCategory;
import com.my.hello.dataobject.ProductInfo;
import com.my.hello.service.ProductCategoryService;
import com.my.hello.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("/list")
    public ResultVO list(){

        //1、查询所有的商品
        List<ProductInfo> infoList = productInfoService.findUpAll();

        //2、查询类目,一次性查询
        List<Integer>integerList = new ArrayList<>();
        for (ProductInfo productInfo : infoList){
             integerList.add(productInfo.getCategoryType());
        }

//        List<Integer>integerList =
//                infoList.stream().map(e ->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory>productCategoryList =
                productCategoryService.findByCategoryTypeIn(integerList);


        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryname(productCategory.getCategoryName());
            productVO.setCategorytype(productCategory.getCategoryType());
            //productVO.setVoList();
        }

        //3、数据拼装
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");

        ProductVO productVO = new ProductVO();
        resultVO.setData(Arrays.asList(productVO));

        ProductInfoVO productInfoVO = new ProductInfoVO();
        productVO.setVoList(Arrays.asList(productInfoVO));
        return resultVO;
    }




}//最外面一层大括号

