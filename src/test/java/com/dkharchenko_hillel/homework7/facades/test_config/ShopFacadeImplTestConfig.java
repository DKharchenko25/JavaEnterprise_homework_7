package com.dkharchenko_hillel.homework7.facades.test_config;

import com.dkharchenko_hillel.homework7.facades.ShopFacade;
import com.dkharchenko_hillel.homework7.facades.ShopFacadeImpl;
import com.dkharchenko_hillel.homework7.services.ShopService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ShopFacadeImplTestConfig {

    @Bean
    public ShopFacade shopFacade(ShopService shopService) {
        return new ShopFacadeImpl(shopService);
    }
}
