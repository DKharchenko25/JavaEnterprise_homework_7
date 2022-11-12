package com.dkharchenko_hillel.homework7.services;


import com.dkharchenko_hillel.homework7.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void addProduct(String name, BigDecimal price, Long shopId);

    void removeProductById(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    void updateProductNameById(Long id, String name);

    void updateProductPriceById(Long id, BigDecimal price);
}
