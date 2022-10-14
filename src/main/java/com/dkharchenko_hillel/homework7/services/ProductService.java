package com.dkharchenko_hillel.homework7.services;


import com.dkharchenko_hillel.homework7.models.Product;

import java.util.List;

public interface ProductService {
    void addProduct(String name, Double price, Long shopId);

    void removeProductById(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    void updateProductNameById(Long id, String name);

    void updateProductPriceById(Long id, Double price);
}
