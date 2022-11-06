package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.facades.PersonFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonFacade personFacade;

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addPersonView() throws Exception {
        mockMvc.perform(get("/add_person"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/addPerson.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void addPerson() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setFirstName("test");
        personDto.setLastName("test");
        personDto.setPhoneNumber("1234");
        personDto.setUsername("test");
        personDto.setPassword("test");
        mockMvc.perform(post("/add_person", personDto))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/all_persons"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void removePersonById() throws Exception {
        mockMvc.perform(delete("/remove_person").param("id", "1"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/all_persons"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void getAllPersons() throws Exception {
        mockMvc.perform(get("/all_persons"))
                .andExpect(status().isOk())
                .andExpect(view().name("allPersons"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/allPersons.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonFirstNameByUsernameView() throws Exception {
        mockMvc.perform(get("/update_first_name"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonFirstName"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonFirstName.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonFirstNameByUsername() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setFirstName("test");
        mockMvc.perform(put("/update_first_name", personDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonFirstNameSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonFirstNameSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonLastNameByUsernameView() throws Exception {
        mockMvc.perform(get("/update_last_name"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonLastName"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonLastName.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonLastNameByUsername() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setLastName("test");
        mockMvc.perform(put("/update_last_name", personDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonLastNameSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonLastNameSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonEmailByUsernameView() throws Exception {
        mockMvc.perform(get("/update_email"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonEmail"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonEmail.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonEmailByUsername() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setEmail("test@gmail.com");
        mockMvc.perform(put("/update_email", personDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonEmailSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonEmailSuccess.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonPhoneNumberByUsernameView() throws Exception {
        mockMvc.perform(get("/update_phone_number"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonPhoneNumber"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonPhoneNumber.jsp"));
    }

    @Test
    @WithMockUser(username = "admin", password = "0000", roles = "ADMIN")
    void updatePersonPhoneNumberByUsername() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setPhoneNumber("221354");
        mockMvc.perform(put("/update_phone_number", personDto))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePersonPhoneNumberSuccess"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/updatePersonPhoneNumberSuccess.jsp"));
    }
}