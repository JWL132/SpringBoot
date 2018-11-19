package com.my.hello.repository;

import com.my.hello.dataobject.SellerInfo;
import com.my.hello.utils.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepostioryTest {

    @Autowired
    private SellerInfoRepostiory repostiory;
    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repostiory.findByOpenid("abc");
    }

    @Test
    public  void  saveTest(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUnique());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo sellerInfo1 = repostiory.save(sellerInfo);
    }
}