package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.dtos.CartDto;

import java.util.List;

public interface CartService {
    Long addCartByPersonUsername(String username);

    Long removeCartById(Long id);

    CartDto getCartById(Long id);

    List<CartDto> getAllCarts();

    List<CartDto> getAllPersonCarts(String username);

    CartDto addProductByProductId(Long cartId, Long productId);

    CartDto removeProductByProductId(Long cartId, Long productId);

    Long removeAllProductsById(Long id);

}
