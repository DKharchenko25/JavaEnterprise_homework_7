package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.dtos.ProductDto;

import java.util.List;

public interface ProductFacade {

    void addProduct(ProductDto dto);

    void removeProduct(ProductDto dto);

    List<ProductDto> getAllProducts();

    void updateProductName(ProductDto dto);

    void updateProductPrice(ProductDto dto);
}
