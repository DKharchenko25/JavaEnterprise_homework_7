package com.dkharchenko_hillel.homework7.services.test_config;

import com.dkharchenko_hillel.homework7.reposiroties.ShopRepository;
import com.dkharchenko_hillel.homework7.services.ShopService;
import com.dkharchenko_hillel.homework7.services.ShopServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ShopServiceImplTestConfig {

    @Bean
    public ShopService shopService(ShopRepository shopRepository) {
        return new ShopServiceImpl(shopRepository);
    }

}
