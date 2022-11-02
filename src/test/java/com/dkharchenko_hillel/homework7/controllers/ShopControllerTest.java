package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.ShopDto;
import com.dkharchenko_hillel.homework7.facades.ShopFacade;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopFacade shopFacade;

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addShopView() throws Exception {
        mockMvc.perform(get("/add_shop").contentType("text/html"))
                .andExpect(status().isOk())
                .andExpect(view().name("addShop"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addShop.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addShopSuccess() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(1L);
        shopDto.setName("test");
        Mockito.doNothing().when(shopFacade).addShop(shopDto);
        mockMvc.perform(post("/add_shop", shopDto))
                .andExpect(status().isOk())
                .andExpect(view().name("addShopSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addShopSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeShopByIdView() throws Exception {
        mockMvc.perform(get("/remove_shop").contentType("text/html"))
                .andExpect(status().isOk())
                .andExpect(view().name("removeShop"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/removeShop.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removeShopById() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(1L);
        Mockito.doNothing().when(shopFacade).removeShop(shopDto);
        mockMvc.perform(delete("/remove_shop", shopDto))
                .andExpect(status().isOk())
                .andExpect(view().name("removeShopSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/removeShopSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void getAllShops() throws Exception {
        mockMvc.perform(get("/all_shops"))
                .andExpect(status().isOk())
                .andExpect(view().name("allShops"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/allShops.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updateShopNameByIdView() throws Exception {
        mockMvc.perform(get("/update_name").contentType("text/html"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateShopName"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updateShopName.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updateShopNameById() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setId(1L);
        shopDto.setName("test");
        mockMvc.perform(put("/update_name", shopDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updateShopNameSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updateShopNameSuccess.jsp"));
    }
}