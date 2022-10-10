package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.dtos.ShopDto;

import java.util.List;

public interface ShopService {
    ShopDto addShop(String name);

    Long removeShopById(Long id);

    ShopDto getShopById(Long id);

    List<ShopDto> getAllShops();

    Long updateShopNameById(Long id, String name);
}
