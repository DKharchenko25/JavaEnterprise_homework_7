package com.dkharchenko_hillel.homework7.facades.test_config;


import com.dkharchenko_hillel.homework7.facades.ProductFacade;
import com.dkharchenko_hillel.homework7.facades.ProductFacadeImpl;
import com.dkharchenko_hillel.homework7.services.ProductService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProductFacadeImplTestConfig {

    @Bean
    public ProductFacade productFacade(ProductService productService) {
        return new ProductFacadeImpl(productService);
    }
}
