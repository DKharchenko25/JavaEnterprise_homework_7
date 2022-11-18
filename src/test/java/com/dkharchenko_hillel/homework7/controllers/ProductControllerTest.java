package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.facades.ProductFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductFacade productFacade;

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addProductView() throws Exception {
        mockMvc.perform(get("/add_product"))
                .andExpect(status().isOk())
                .andExpect(view().name("addProduct"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addProduct.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("test");
        productDto.setPriceInUah(new BigDecimal("20.0"));
        productDto.setShopId(1L);
        doNothing().when(productFacade).addProduct(productDto);
        mockMvc.perform(post("/add_product", productDto))
                .andExpect(status().isOk())
                .andExpect(view().name("addProductSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addProductSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeProductByIdView() throws Exception {
        mockMvc.perform(get("/remove_product"))
                .andExpect(status().isOk())
                .andExpect(view().name("removeProduct"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/removeProduct.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeProductById() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        doNothing().when(productFacade).removeProduct(productDto);
        mockMvc.perform(delete("/remove_product", productDto))
                .andExpect(status().isOk())
                .andExpect(view().name("removeProductSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/removeProductSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/all_products"))
                .andExpect(status().isOk())
                .andExpect(view().name("allProducts"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/allProducts.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updateProductNameByIdView() throws Exception {
        mockMvc.perform(get("/update_product_name"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProductName"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updateProductName.jsp"));

    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updateProductNameById() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("test");
        doNothing().when(productFacade).updateProductName(productDto);
        mockMvc.perform(put("/update_product_name", productDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProductNameSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updateProductNameSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updateProductPriceByIdView() throws Exception {
        mockMvc.perform(get("/update_price"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProductPrice"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updateProductPrice.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updateProductPriceById() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setPriceInUah(new BigDecimal("20.0"));
        doNothing().when(productFacade).updateProductName(productDto);
        mockMvc.perform(put("/update_price", productDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProductPriceSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updateProductPriceSuccess.jsp"));
    }
}