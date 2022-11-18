package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.converters.ProductConverter;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.facades.test_config.ProductFacadeImplTestConfig;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductFacadeImplTestConfig.class)
class ProductFacadeImplTest {

    @Autowired
    private ProductFacade productFacade;

    @MockBean
    private ProductService productService;

    @Test
    void addProductSuccess() {
        ProductDto productDto = new ProductDto();
        productDto.setName("success");
        productDto.setPriceInUah(new BigDecimal("20.0"));
        productDto.setShopId(1L);
        doNothing().when(productService).addProduct("success", new BigDecimal("20.0"), 1L);
        productFacade.addProduct(productDto);
        verify(productService, times(1)).addProduct("success", new BigDecimal("20.0"), 1L);
    }

    @Test
    void addProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productFacade.addProduct(null));
    }

    @ParameterizedTest
    @MethodSource("invalidInputsSource")
    void addProductMustThrowIllegalArgumentException(String name) {
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setPriceInUah(new BigDecimal("20.0"));
        productDto.setShopId(1L);
        assertThrows(IllegalArgumentException.class, () -> productFacade.addProduct(productDto));

        productDto.setName("fail");
        doThrow(NotFoundException.class).when(productService).addProduct(productDto.getName(),
                productDto.getPriceInUah(), productDto.getShopId());
        assertThrows(NotFoundException.class, () -> productFacade.addProduct(productDto));

    }

    private static Stream<String> invalidInputsSource() {
        return Stream.of("", "--__", "??!");
    }

    @Test
    void removeProductSuccess() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        doNothing().when(productService).removeProductById(1L);
        productFacade.removeProduct(productDto);
        verify(productService, times(1)).removeProductById(1L);
    }

    @Test
    void removeProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productFacade.removeProduct(null));
    }

    @Test
    void removeProductMustThrowNotFoundException() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        doThrow(NotFoundException.class).when(productService).removeProductById(1L);
        assertThrows(NotFoundException.class, () -> productFacade.removeProduct(productDto));
    }


    @Test
    void getAllProductsSuccess() {
        List<Product> productList = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productList);
        assertEquals(productList.stream().map(ProductConverter::convertProductToProductDto)
                .collect(Collectors.toList()), productFacade.getAllProducts());
    }

    @Test
    void updateProductNameSuccess() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("success");
        doNothing().when(productService).updateProductNameById(1L, "success");
        productFacade.updateProductName(productDto);
        verify(productService, times(1)).updateProductNameById(1L, "success");
    }

    @Test
    void updateProductNameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productFacade.updateProductName(null));
    }

    @ParameterizedTest
    @MethodSource("invalidInputsSource")
    void updateProductNameMustThrowIllegalArgumentException(String name) {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName(name);
        assertThrows(IllegalArgumentException.class, () -> productFacade.updateProductName(productDto));

        productDto.setName("fail");
        doThrow(NotFoundException.class).when(productService).updateProductNameById(1L, "fail");
        assertThrows(NotFoundException.class, () -> productFacade.updateProductName(productDto));
    }

    @Test
    void updateProductPriceSuccess() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setPriceInUah(new BigDecimal("20.0"));
        doNothing().when(productService).updateProductPriceById(1L, new BigDecimal("20.0"));
        productFacade.updateProductPrice(productDto);
        verify(productService, times(1)).updateProductPriceById(1L, new BigDecimal("20.0"));
    }

    @Test
    void updateProductPriceMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productFacade.updateProductPrice(null));
    }

    @Test
    void updateProductPriceMustThrowNotFoundException() {
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setPriceInUah(new BigDecimal("20.0"));
        doThrow(NotFoundException.class).when(productService).updateProductPriceById(2L, new BigDecimal("20.0"));
        assertThrows(NotFoundException.class, () -> productFacade.updateProductPrice(productDto));
    }
}