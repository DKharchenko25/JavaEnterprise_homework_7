package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.converters.CartConverter;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.facades.CartFacade;
import com.dkharchenko_hillel.homework7.models.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartFacade cartFacade;

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addCart() throws Exception {
        mockMvc.perform(post("/add_cart"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/person_carts"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeCartById() throws Exception {
        mockMvc.perform(delete("/remove_cart").param("id", "1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/person_carts"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void getCartById() throws Exception {
        Cart cart = new Cart();
        cart.setId(1L);
        Mockito.when(cartFacade.getCartById(1L)).thenReturn(CartConverter.convertCartToCartDto(cart));
        mockMvc.perform(get("/get_cart").param("id", "1"))
                .andExpect(status().is(200))
                .andExpect(view().name("getCartSuccess"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void getAllCarts() throws Exception {
        mockMvc.perform(get("/all_carts"))
                .andExpect(status().is(200))
                .andExpect(view().name("allCarts"));

    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void getAllPersonCarts() throws Exception {
        mockMvc.perform(get("/person_carts"))
                .andExpect(status().is(200))
                .andExpect(view().name("allPersonsCarts"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addProductByProductIdView() throws Exception {
        mockMvc.perform(get("/add_to_cart"))
                .andExpect(status().is(200))
                .andExpect(view().name("addProductToCart"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addProductByProductId() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setCartId(1L);
        Mockito.doNothing().when(cartFacade).addProductById(productDto);
        mockMvc.perform(put("/add_to_cart").param("cartId", "1").param("productId", "1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/get_cart?id=" + productDto.getCartId()));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeProductByProductId() throws Exception {
        mockMvc.perform(put("/remove_from_cart").param("cartId", "1").param("productId", "1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/get_cart?id=" + 1));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeAllProductsById() throws Exception {
        mockMvc.perform(put("/remove_all").param("id", "1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/person_carts"));
    }
}