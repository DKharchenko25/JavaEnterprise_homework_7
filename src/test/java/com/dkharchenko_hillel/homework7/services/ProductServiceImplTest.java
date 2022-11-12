package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.models.Shop;
import com.dkharchenko_hillel.homework7.reposiroties.ProductRepository;
import com.dkharchenko_hillel.homework7.services.test_config.ProductServiceImplTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductServiceImplTestConfig.class)
class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ShopService shopService;
    @Autowired
    private ProductService productService;

    @Test
    void addProductSuccess() {
        Shop shop = new Shop("test");
        when(shopService.getShopById(2L)).thenReturn(shop);
        productService.addProduct("test", new BigDecimal("20.0"), 2L);
        verify(productRepository, times(1)).save(any(Product.class));
        assertFalse(shop.getProducts().isEmpty());
    }

    @Test
    void addProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productService.addProduct(null, new BigDecimal("20.0"), 1L));
        assertThrows(NullPointerException.class, () -> productService.addProduct("test", null, 1L));
        assertThrows(NullPointerException.class, () -> productService.addProduct("test", new BigDecimal("20.0"), null));
        assertThrows(NullPointerException.class, () -> productService.addProduct(null, null, null));
    }

    @Test
    void addProductMustThrowNotFoundException() {
        when(shopService.getShopById(1L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> productService.addProduct("test", new BigDecimal("20.0"), 1L));
    }

    @Test
    void removeProductByIdSuccess() {
        Shop shop = new Shop("test");
        shop.setId(3L);
        Product product = new Product();
        product.setShop(shop);
        when(shopService.getShopById(3L)).thenReturn(shop);
        when(productRepository.existsById(3L)).thenReturn(true);
        when(productRepository.findById(3L)).thenReturn(Optional.of(product));
        productService.removeProductById(3L);
        verify(productRepository, times(1)).deleteById(anyLong());
        assertFalse(shop.getProducts().contains(product));
    }

    @Test
    void removeProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productService.removeProductById(null));
    }

    @Test
    void removeProductMustThrowNotFoundException() {
        when(productRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> productService.removeProductById(1L));
        when(shopService.getShopById(2L)).thenThrow(IllegalArgumentException.class);
        assertThrows(NotFoundException.class, () -> productService.removeProductById(2L));
    }

    @Test
    void getProductByIdSuccess() {
        Product product = new Product();
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        assertEquals(product, productService.getProductById(2L));
    }

    @Test
    void getProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productService.getProductById(null));
    }

    @Test
    void getProductMustThrowNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void getAllProductsSuccess() {
        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(productList, productService.getAllProducts());
    }

    @Test
    void updateProductNameByIdSuccess() {
        when(productRepository.existsById(2L)).thenReturn(true);
        productService.updateProductNameById(2L, "test");
        verify(productRepository, times(1)).updateProductNameById(2L, "test");
    }

    @Test
    void updateProductNameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productService.updateProductNameById(1L, null));
        assertThrows(NullPointerException.class, () -> productService.updateProductNameById(null, "test"));
        assertThrows(NullPointerException.class, () -> productService.updateProductNameById(null, null));
    }

    @Test
    void updateProductNameMustThrowNotFoundException() {
        when(productRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> productService.updateProductNameById(1L, "test"));
    }

    @Test
    void updateProductPriceByIdSuccess() {
        when(productRepository.existsById(2L)).thenReturn(true);
        productService.updateProductPriceById(2L, new BigDecimal("20.0"));
        verify(productRepository, times(1)).updateProductPriceById(2L, new BigDecimal("20.0"));
    }

    @Test
    void updateProductPriceMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> productService.updateProductPriceById(null, new BigDecimal("20.0")));
        assertThrows(NullPointerException.class, () -> productService.updateProductPriceById(1L, null));
        assertThrows(NullPointerException.class, () -> productService.updateProductPriceById(null, null));
    }

    @Test
    void updateProductPriceMustThrowNotFoundException() {
        when(productRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> productService.updateProductPriceById(1L, new BigDecimal("20.0")));
    }
}