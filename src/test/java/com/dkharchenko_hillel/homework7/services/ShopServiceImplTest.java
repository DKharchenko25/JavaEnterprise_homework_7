package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Shop;
import com.dkharchenko_hillel.homework7.reposiroties.ShopRepository;
import com.dkharchenko_hillel.homework7.services.test_config.ShopServiceImplTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShopServiceImplTestConfig.class)
class ShopServiceImplTest {

    @MockBean
    private ShopRepository shopRepository;
    @Autowired
    private ShopService shopService;

    @Test
    void addShopSuccess() {
        Shop shop = new Shop("test");
        when(shopRepository.save(shop)).thenReturn(shop);
        shopService.addShop("test");
        verify(shopRepository, times(1)).save(any(Shop.class));
    }

    @Test
    void addShopMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopService.addShop(null));
    }

    @Test
    void removeShopByIdSuccess() {
        when(shopRepository.existsById(anyLong())).thenReturn(true);
        shopService.removeShopById(1L);
        verify(shopRepository, times(1)).deleteById(1L);
    }

    @Test
    void removeShopMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopService.removeShopById(null));
    }

    @Test
    void removeShopMustThrowNotFoundException() {
        when(shopRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> shopService.removeShopById(1L));
    }

    @Test
    void getShopByIdSuccess() {
        Shop shop = new Shop("test");
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        assertEquals(shop, shopService.getShopById(1L));
    }

    @Test
    void getShopMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopService.getShopById(null));
    }

    @Test
    void getShopMustThrowNotFoundException() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> shopService.getShopById(1L));
    }

    @Test
    void getAllShopsSuccess() {
        List<Shop> shopList = new ArrayList<>();
        when(shopRepository.findAll()).thenReturn(shopList);
        assertEquals(shopList, shopService.getAllShops());
    }

    @Test
    void updateShopNameByIdSuccess() {
        when(shopRepository.existsById(1L)).thenReturn(true);
        shopService.updateShopNameById(1L, "test");
        verify(shopRepository, times(1)).updateNameById(1L, "test");
    }

    @Test
    void updateShopMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopService.updateShopNameById(1L, null));
        assertThrows(NullPointerException.class, () -> shopService.updateShopNameById(null, "test"));
        assertThrows(NullPointerException.class, () -> shopService.updateShopNameById(null, null));
    }

    @Test
    void updateShopMustThrowNotFoundException() {
        when(shopRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> shopService.updateShopNameById(1L, "test"));
    }
}