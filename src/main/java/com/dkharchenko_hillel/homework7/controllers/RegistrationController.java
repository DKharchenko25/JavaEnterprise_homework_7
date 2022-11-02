package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.facades.PersonFacade;
import com.dkharchenko_hillel.homework7.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {

    private final PersonFacade personFacade;


    public RegistrationController(PersonFacade personFacade) {
        this.personFacade = personFacade;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("personForm", new Person());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPerson(@ModelAttribute("personForm") PersonDto personDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!personDto.getPassword().equals(personDto.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Wrong password");
            return "registration";
        }
        if (personFacade.getPersonByUsername(personDto.getUsername()).getUsername().equals(personDto.getUsername())) {
            model.addAttribute("usernameError", "User with this username is already exists");
            return "registration";
        }
        personFacade.addPerson(personDto);
        log.info("New user is registered: {}", personDto.getUsername());
        return "redirect:/main";
    }

}
