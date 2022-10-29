package com.dkharchenko_hillel.homework7.services.test_config;

import com.dkharchenko_hillel.homework7.reposiroties.CartRepository;
import com.dkharchenko_hillel.homework7.services.CartService;
import com.dkharchenko_hillel.homework7.services.CartServiceImpl;
import com.dkharchenko_hillel.homework7.services.PersonService;
import com.dkharchenko_hillel.homework7.services.ProductService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CartServiceImplTestConfig {

    @Bean
    public CartService cartService(CartRepository cartRepository, PersonService personService, ProductService productService) {
        return new CartServiceImpl(cartRepository, personService, productService);
    }
}
