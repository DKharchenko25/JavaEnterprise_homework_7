package com.dkharchenko_hillel.homework7.services;


import com.dkharchenko_hillel.homework7.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    Long addProduct(ProductDto dto);

    Long removeProductById(Long id);

    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts();

    Long updateProductNameById(Long id, ProductDto dto);

    Long updateProductPriceById(Long id, ProductDto dto);
}
