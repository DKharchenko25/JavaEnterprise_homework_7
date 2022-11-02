package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Shop;
import com.dkharchenko_hillel.homework7.reposiroties.ShopRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public void addShop(@NonNull String name) {
        shopRepository.save(new Shop(name));
    }


    @Override
    public void removeShopById(@NonNull Long id) {
        if (shopRepository.existsById(id)) {
            shopRepository.deleteById(id);
        } else {
            log.error(getExceptionMessage(id));
            throw new NotFoundException(getExceptionMessage(id));
        }
    }

    @Override
    public Shop getShopById(@NonNull Long id) {
        if (shopRepository.findById(id).isPresent()) {
            return shopRepository.findById(id).get();
        } else {
            log.error(getExceptionMessage(id));
            throw new NotFoundException(getExceptionMessage(id));
        }
    }

    @Override
    public List<Shop> getAllShops() {
        return (List<Shop>) shopRepository.findAll();
    }

    @Override
    public void updateShopNameById(@NonNull Long id, @NonNull String name) {
        if (shopRepository.existsById(id)) {
            shopRepository.updateNameById(id, name);
        } else {
            log.error(getExceptionMessage(id));
            throw new NotFoundException(getExceptionMessage(id));
        }
    }

    private static String getExceptionMessage(Long id) {
        return "Shop with ID #" + id + " is not found";
    }
}
