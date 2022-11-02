package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.dtos.ShopDto;

import java.util.List;

public interface ShopFacade {

    void addShop(ShopDto dto);

    void removeShop(ShopDto dto);

    List<ShopDto> getAllShops();

    void updateShopName(ShopDto dto);

}
