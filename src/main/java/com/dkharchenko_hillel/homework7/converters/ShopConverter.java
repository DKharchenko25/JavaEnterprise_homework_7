package com.dkharchenko_hillel.homework7.converters;

import com.dkharchenko_hillel.homework7.dtos.ShopDto;
import com.dkharchenko_hillel.homework7.models.Shop;

public class ShopConverter {
    public static ShopDto convertShopToShopDto(Shop shop) {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(shop.getId());
        shopDto.setName(shop.getName());
        shopDto.setProducts(shop.getProducts());
        return shopDto;
    }
}
