package com.my.hello.service.impl;

import com.my.hello.dataobject.ProductInfo;
import com.my.hello.dto.CartDTO;
import com.my.hello.enums.ProductStatusEnum;
import com.my.hello.enums.ResultEnum;
import com.my.hello.exception.SellException;
import com.my.hello.repository.ProductInfoRepository;
import com.my.hello.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /** */
    @Override
    @Transactional //事务回滚,如果某一条失败,那么都将重新获取
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if(productInfo == null){
                /**
                 * 如果ProductInfo里面为空,那么将抛出一个异常 "商品不存在"
                 */
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
            /**
             * 定义一个变量用于接收返回的库存,订单表里面库存减去用户下单的数量
             */
            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            /**
             * 将订单表的库存 设置为新的状态
             */
            productInfo.setProductStock(result);
            /**
             * 将新库存设置进去
             */
            repository.save(productInfo);
            }
        }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
           ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
           if(productInfo == null){
               /**
                * 如果ProductInfo里面为空,那么将抛出一个异常 "商品不存在"
                */
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
           Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                /**
                 * 如果返回的订单数量大于库存的数量,那么将抛出一个异常 "库存不足,请稍后下单"
                 */
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            /**
             * 将订单表的库存 设置为新的状态
             */
            productInfo.setProductStock(result);
            /**
             * 将新库存设置进去
             */
            repository.save(productInfo);
        }
    }
}
