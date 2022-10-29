package com.dkharchenko_hillel.homework7.services.test_config;

import com.dkharchenko_hillel.homework7.reposiroties.ProductRepository;
import com.dkharchenko_hillel.homework7.services.ProductService;
import com.dkharchenko_hillel.homework7.services.ProductServiceImpl;
import com.dkharchenko_hillel.homework7.services.ShopService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProductServiceImplTestConfig {

    @Bean
    public ProductService productService(ProductRepository productRepository, ShopService shopService) {
        return new ProductServiceImpl(productRepository, shopService);
    }
}
