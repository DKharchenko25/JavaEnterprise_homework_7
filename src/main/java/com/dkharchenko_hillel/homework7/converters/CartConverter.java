package com.dkharchenko_hillel.homework7.converters;

import com.dkharchenko_hillel.homework7.dtos.CartDto;
import com.dkharchenko_hillel.homework7.models.Cart;

public class CartConverter {

    public static CartDto convertCartToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setPerson(cart.getPerson());
        cartDto.setProducts(cart.getProducts());
        cartDto.setAmountOfProducts(cart.getAmountOfProducts());
        cartDto.setSum(cart.getSum());
        return cartDto;
    }
}
