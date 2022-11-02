package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.converters.CartConverter;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.facades.test_config.CartFacadeImplTestConfig;
import com.dkharchenko_hillel.homework7.models.Cart;
import com.dkharchenko_hillel.homework7.services.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CartFacadeImplTestConfig.class)
class CartFacadeImplTest {

    @Autowired
    private CartFacade cartFacade;

    @MockBean
    private CartService cartService;

    @Test
    void addCartByPersonUsernameSuccess() {
        doNothing().when(cartService).addCartByPersonUsername("success");
        cartFacade.addCartByPersonUsername("success");
        verify(cartService, times(1)).addCartByPersonUsername("success");
    }

    @Test
    void addCartByPersonUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.addCartByPersonUsername(null));
    }

    @Test
    void addCartByPersonUsernameMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(cartService).addCartByPersonUsername("fail");
        assertThrows(NotFoundException.class, () -> cartFacade.addCartByPersonUsername("fail"));
    }

    @Test
    void removeCartByIdSuccess() {
        doNothing().when(cartService).removeCartById(1L);
        cartFacade.removeCartById(1L);
        verify(cartService, times(1)).removeCartById(1L);
    }

    @Test
    void removeCartByIdMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.removeCartById(null));
    }

    @Test
    void removeCartByIdMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(cartService).removeCartById(1L);
        assertThrows(NotFoundException.class, () -> cartFacade.removeCartById(1L));
    }

    @Test
    void getCartByIdSuccess() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cartService.getCartById(1L)).thenReturn(cart);
        assertEquals(CartConverter.convertCartToCartDto(cart).getId(), cartFacade.getCartById(1L).getId());
    }

    @Test
    void getCartByIdMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.getCartById(null));
    }

    @Test
    void getCartByIdMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(cartService).getCartById(1L);
        assertThrows(NotFoundException.class, () -> cartFacade.getCartById(1L));
    }


    @Test
    void getAllCartsSuccess() {
        List<Cart> cartList = new ArrayList<>();
        when(cartService.getAllCarts()).thenReturn(cartList);
        assertEquals(cartList.stream().map(CartConverter::convertCartToCartDto).collect(Collectors.toList()),
                cartFacade.getAllCarts());
    }

    @Test
    void getAllPersonCartsSuccess() {
        List<Cart> cartList = new ArrayList<>();
        when(cartService.getAllPersonCarts("success")).thenReturn(cartList);
        assertEquals(cartList.stream().map(CartConverter::convertCartToCartDto).collect(Collectors.toList()),
                cartFacade.getAllPersonCarts("success"));
    }

    @Test
    void getAllPersonCartsMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.getAllPersonCarts(null));
    }

    @Test
    void getAllPersonCartsMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(cartService).getAllPersonCarts("fail");
        assertThrows(NotFoundException.class, () -> cartFacade.getAllPersonCarts("fail"));
    }

    @Test
    void addProductByIdSuccess() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setCartId(1L);
        doNothing().when(cartService).addProductByProductId(1L, 1L);
        cartFacade.addProductById(productDto);
        verify(cartService, times(1)).addProductByProductId(1L, 1L);
    }

    @Test
    void addProductByIdMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.addProductById(null));
    }

    @Test
    void addProductByIdMustThrowNotFoundException() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setCartId(1L);
        doThrow(NotFoundException.class).when(cartService).addProductByProductId(1L, 1L);
        assertThrows(NotFoundException.class, () -> cartFacade.addProductById(productDto));
    }

    @Test
    void removeProductByIdSuccess() {
        doNothing().when(cartService).removeProductByProductId(1L, 1L);
        cartFacade.removeProductById(1L, 1L);
        verify(cartService, times(1)).removeProductByProductId(1L, 1L);
    }

    @Test
    void removeProductByIdMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.removeProductById(null, 1L));
        assertThrows(NullPointerException.class, () -> cartFacade.removeProductById(1L, null));
        assertThrows(NullPointerException.class, () -> cartFacade.removeProductById(null, null));
    }

    @Test
    void removeProductByIdMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(cartService).removeProductByProductId(1L, 1L);
        assertThrows(NotFoundException.class, () -> cartFacade.removeProductById(1L, 1L));
    }

    @Test
    void removeAllProductsByIdSuccess() {
        doNothing().when(cartService).removeAllProductsById(1L);
        cartFacade.removeAllProductsById(1L);
        verify(cartService, times(1)).removeAllProductsById(1L);
    }

    @Test
    void removeAllProductsByIdMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartFacade.removeAllProductsById(null));
    }

    @Test
    void removeAllProductsByIdMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(cartService).removeAllProductsById(1L);
        assertThrows(NotFoundException.class, () -> cartFacade.removeAllProductsById(1L));
    }
}