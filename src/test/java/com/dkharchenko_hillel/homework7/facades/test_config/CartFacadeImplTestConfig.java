package com.dkharchenko_hillel.homework7.facades.test_config;

import com.dkharchenko_hillel.homework7.facades.CartFacade;
import com.dkharchenko_hillel.homework7.facades.CartFacadeImpl;
import com.dkharchenko_hillel.homework7.services.CartService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CartFacadeImplTestConfig {

    @Bean
    public CartFacade cartFacade(CartService cartService) {
        return new CartFacadeImpl(cartService);
    }
}
