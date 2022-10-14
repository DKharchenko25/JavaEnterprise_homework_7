package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.models.Shop;

import java.util.List;

public interface ShopService {
    void addShop(String name);

    void removeShopById(Long id);

    Shop getShopById(Long id);

    List<Shop> getAllShops();

    void updateShopNameById(Long id, String name);
}
