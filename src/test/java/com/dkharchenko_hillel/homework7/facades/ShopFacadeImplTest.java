package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.converters.ShopConverter;
import com.dkharchenko_hillel.homework7.dtos.ShopDto;
import com.dkharchenko_hillel.homework7.facades.test_config.ShopFacadeImplTestConfig;
import com.dkharchenko_hillel.homework7.models.Shop;
import com.dkharchenko_hillel.homework7.services.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShopFacadeImplTestConfig.class)
class ShopFacadeImplTest {

    @Autowired
    private ShopFacade shopFacade;

    @MockBean
    private ShopService shopService;


    @Test
    void addShopSuccess() {
        ShopDto shopDto = new ShopDto();
        shopDto.setName("success");
        doNothing().when(shopService).addShop(shopDto.getName());
        shopFacade.addShop(shopDto);
        verify(shopService, times(1)).addShop(shopDto.getName());
    }

    @Test
    void addShopMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopFacade.addShop(null));
    }

    @ParameterizedTest
    @MethodSource("invalidNamesSource")
    void addShopMustThrowIllegalArgumentException(String name) {
        ShopDto shopDto = new ShopDto();
        shopDto.setName(name);
        assertThrows(IllegalArgumentException.class, () -> shopFacade.addShop(shopDto));
    }

    private static Stream<String> invalidNamesSource() {
        return Stream.of("", "?--2", "(-+");
    }

    @Test
    void removeShopMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopFacade.removeShop(null));
    }

    @Test
    void removeShopMustThrowIllegalArgumentException() {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(1L);
        doThrow(NotFoundException.class).when(shopService).removeShopById(1L);
        assertThrows(NotFoundException.class, () -> shopFacade.removeShop(shopDto));
    }

    @Test
    void getAllShopsSuccess() {
        List<Shop> shopList = new ArrayList<>();
        when(shopService.getAllShops()).thenReturn(shopList);
        assertEquals(shopList.stream().map(ShopConverter::convertShopToShopDto)
                .collect(Collectors.toList()), shopFacade.getAllShops());
    }

    @Test
    void updateShopSuccess() {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(1L);
        shopDto.setName("success");
        doNothing().when(shopService).updateShopNameById(1L, "success");
        shopFacade.updateShopName(shopDto);
        verify(shopService, times(1)).updateShopNameById(1L, "success");
    }

    @ParameterizedTest
    @MethodSource("invalidNamesSource")
    void updateShopNameMustThrowIllegalArgumentException(String name) {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(1L);
        shopDto.setName(name);
        assertThrows(IllegalArgumentException.class, () -> shopFacade.updateShopName(shopDto));

        shopDto.setName("fail");
        doThrow(NotFoundException.class).when(shopService).updateShopNameById(shopDto.getId(), shopDto.getName());
        assertThrows(NotFoundException.class, () -> shopFacade.updateShopName(shopDto));
    }

    @Test
    void updateShopNameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> shopFacade.updateShopName(null));
    }
}