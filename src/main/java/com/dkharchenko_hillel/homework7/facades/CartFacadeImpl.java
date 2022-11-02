package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.converters.CartConverter;
import com.dkharchenko_hillel.homework7.dtos.CartDto;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.services.CartService;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.dkharchenko_hillel.homework7.converters.CartConverter.convertCartToCartDto;

@Component
public class CartFacadeImpl implements CartFacade {

    private final CartService cartService;

    public CartFacadeImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void addCartByPersonUsername(@NonNull String username) {
        cartService.addCartByPersonUsername(username);
    }

    @Override
    public void removeCartById(@NonNull Long id) {
        cartService.removeCartById(id);
    }

    @Override
    public CartDto getCartById(@NonNull Long id) {
        return convertCartToCartDto(cartService.getCartById(id));
    }

    @Override
    public List<CartDto> getAllCarts() {
        return cartService.getAllCarts().stream().map(CartConverter::convertCartToCartDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartDto> getAllPersonCarts(@NonNull String username) {
        return cartService.getAllPersonCarts(username).stream().map(CartConverter::convertCartToCartDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addProductById(@NonNull ProductDto dto) {
        cartService.addProductByProductId(dto.getCartId(), dto.getId());
    }

    @Override
    public void removeProductById(@NonNull Long cartId, @NonNull Long productId) {
        cartService.removeProductByProductId(cartId, productId);
    }

    @Override
    public void removeAllProductsById(@NonNull Long id) {
        cartService.removeAllProductsById(id);
    }
}
