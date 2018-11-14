package com.my.hello;

import com.my.hello.dataobject.ProductCategory;
import com.my.hello.repository.ProductCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTests {

    @Autowired

    private ProductCategoryRepository repository;


     @Test
    public void contextLoads() {

    }




}
