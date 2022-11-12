package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.converters.ShopConverter;
import com.dkharchenko_hillel.homework7.dtos.ShopDto;
import com.dkharchenko_hillel.homework7.services.ShopService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.dkharchenko_hillel.homework7.validators.InputValidator.checkName;

@Slf4j
@Component
public class ShopFacadeImpl implements ShopFacade {

    private final ShopService shopService;

    public ShopFacadeImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public void addShop(@NonNull ShopDto dto) {
        shopService.addShop(checkName(dto.getName()));
    }

    @Override
    public void removeShop(@NonNull ShopDto dto) {
        shopService.removeShopById(dto.getId());
    }

    @Override
    public List<ShopDto> getAllShops() {
        return shopService.getAllShops().stream().map(ShopConverter::convertShopToShopDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateShopName(@NonNull ShopDto dto) {
        shopService.updateShopNameById(dto.getId(), checkName(dto.getName()));
    }
}
