package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.dtos.CartDto;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;

import java.util.List;

public interface CartFacade {

    void addCartByPersonUsername(String username);

    void removeCartById(Long id);

    CartDto getCartById(Long id);

    List<CartDto> getAllCarts();

    List<CartDto> getAllPersonCarts(String username);

    void addProductById(ProductDto dto);

    void removeProductById(Long cartId, Long productId);

    void removeAllProductsById(Long id);
}
