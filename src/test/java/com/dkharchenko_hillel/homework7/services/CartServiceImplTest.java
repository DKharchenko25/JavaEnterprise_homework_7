package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Cart;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.reposiroties.CartRepository;
import com.dkharchenko_hillel.homework7.services.test_config.CartServiceImplTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CartServiceImplTestConfig.class)
class CartServiceImplTest {

    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private PersonService personService;
    @MockBean
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @Test
    void addCartSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personService.getPersonByUsername("success")).thenReturn(person);
        cartService.addCartByPersonUsername("success");
        verify(cartRepository, times(1)).save(any(Cart.class));
        assertFalse(person.getCarts().isEmpty());
    }

    @Test
    void addCartMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.addCartByPersonUsername(null));
    }

    @Test
    void addCartMustThrowNotFoundException() {
        when(personService.getPersonByUsername("test")).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> cartService.addCartByPersonUsername("test"));
    }

    @Test
    void removeCartByIdSuccess() {
        Cart cart = new Cart();
        Person person = new Person();
        person.setId(1L);
        cart.setPerson(person);
        when(cartRepository.findById(2L)).thenReturn(Optional.of(cart));
        when(personService.getPersonById(cart.getPerson().getId())).thenReturn(person);
        cartService.removeCartById(2L);
        verify(cartRepository, times(1)).deleteById(2L);
        assertFalse(person.getCarts().contains(cart));
    }

    @Test
    void removeCartMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.removeCartById(null));
    }

    @Test
    void removeCartMustThrowNotFoundExceptionn() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cartService.removeCartById(1L));
        when(personService.getPersonById(3L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> cartService.removeCartById(3L));
    }

    @Test
    void getCartByIdSuccess() {
        Cart cart = new Cart();
        when(cartRepository.findById(2L)).thenReturn(Optional.of(cart));
        assertEquals(cart, cartService.getCartById(2L));
    }

    @Test
    void getCartMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.getCartById(null));
    }

    @Test
    void getCartMustThrowNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cartService.getCartById(1L));
    }

    @Test
    void getAllCartsSuccess() {
        List<Cart> cartList = new ArrayList<>();
        when(cartRepository.findAll()).thenReturn(cartList);
        assertEquals(cartList, cartService.getAllCarts());
    }

    @Test
    void getAllPersonCartsSuccess() {
        List<Cart> cartList = new ArrayList<>();
        Person person = new Person();
        person.setUsername("success");
        person.setCarts(cartList);
        when(personService.getPersonByUsername("success")).thenReturn(person);
        assertEquals(cartList, cartService.getAllPersonCarts("success"));
    }

    @Test
    void getAllPersonCartsMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.getAllPersonCarts(null));
    }

    @Test
    void getAllPersonCartsMustThrowNotFoundException() {
        when(personService.getPersonByUsername("test")).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> cartService.getAllPersonCarts("test"));
    }

    @Test
    void addProductByProductIdSuccess() {
        Cart cart = new Cart();
        cart.setId(2L);
        Product product = new Product();
        product.setId(2L);
        product.setPrice(new BigDecimal("20.0"));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(productService.getProductById(product.getId())).thenReturn(product);
        cartService.addProductByProductId(cart.getId(), product.getId());
        assertTrue(cart.getProducts().contains(product));
        assertEquals(0, cart.getSum().compareTo(product.getPrice()));
    }

    @Test
    void addProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.addProductByProductId(1L, null));
        assertThrows(NullPointerException.class, () -> cartService.addProductByProductId(null, 1L));
        assertThrows(NullPointerException.class, () -> cartService.addProductByProductId(null, null));
    }

    @Test
    void addProductMustThrowNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cartService.addProductByProductId(1L, 1L));

        Cart exceptionCart = new Cart();
        exceptionCart.setId(3L);
        when(cartRepository.findById(3L)).thenReturn(Optional.of(exceptionCart));
        when(productService.getProductById(3L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> cartService.addProductByProductId(3L, 3L));
    }

    @Test
    void removeProductByProductIdSuccess() {
        Cart cart = new Cart();
        cart.setId(3L);
        Product product = new Product();
        product.setId(3L);
        product.setPrice(new BigDecimal("20.0"));
        cart.getProducts().add(product);
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(productService.getProductById(product.getId())).thenReturn(product);
        cartService.removeProductByProductId(cart.getId(), product.getId());
        assertFalse(cart.getProducts().contains(product));
        assertEquals(0, cart.getSum().intValue());
    }

    @Test
    void removeProductMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.removeProductByProductId(1L, null));
        assertThrows(NullPointerException.class, () -> cartService.removeProductByProductId(null, 1L));
        assertThrows(NullPointerException.class, () -> cartService.removeProductByProductId(null, null));
    }

    @Test
    void removeProductMustThrowNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cartService.removeProductByProductId(1L, 1L));

        Cart exceptionCart = new Cart();
        exceptionCart.setId(2L);
        when(productService.getProductById(2L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> cartService.removeProductByProductId(2L, 2L));
    }


    @Test
    void removeAllProductsById() {
        Cart cart = new Cart();
        cart.setId(2L);
        List<Product> productList = new ArrayList<>(Arrays.asList(new Product(), new Product(), new Product()));

        cart.setProducts(productList);
        when(cartRepository.findById(2L)).thenReturn(Optional.of(cart));
        cartService.removeAllProductsById(cart.getId());
        assertTrue(cart.getProducts().isEmpty());
        assertEquals(0, cart.getSum().intValue());
        assertEquals(0, cart.getAmountOfProducts());
    }

    @Test
    void removeAllMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.removeAllProductsById(null));
    }

    @Test
    void removeAllMustThrowNotFoundException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cartService.removeAllProductsById(1L));
    }
}