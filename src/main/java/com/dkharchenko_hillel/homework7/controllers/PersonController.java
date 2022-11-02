package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.facades.PersonFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class PersonController {
    private final PersonFacade personFacade;

    private final HttpServletRequest httpServletRequest;


    public PersonController(PersonFacade personFacade, HttpServletRequest httpServletRequest) {
        this.personFacade = personFacade;
        this.httpServletRequest = httpServletRequest;
    }


    @RequestMapping(value = "/add_person", method = RequestMethod.GET)
    public String addPersonView(Model model) {
        model.addAttribute("person", new PersonDto());
        return "addPerson";
    }

    @RequestMapping(value = "/add_person", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("person") PersonDto personDto) {
        personFacade.addPerson(personDto);
        log.info("New customer is added to persons table: {}", personDto.getUsername());
        return "redirect:/all_persons";
    }

    @RequestMapping(value = "/remove_person", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removePersonById(@RequestParam Long id) {
        personFacade.removePerson(id);
        log.info("Customer is removed from persons table: {}", id);
        return "redirect:/all_persons";
    }

    @GetMapping("/all_persons")
    public String getAllPersons(Model model) {
        model.addAttribute("all", personFacade.getAllPersons());
        return "allPersons";
    }

    @RequestMapping(value = "/update_first_name", method = RequestMethod.GET)
    public String updatePersonFirstNameByUsernameView(Model model) {
        model.addAttribute("person", new PersonDto());
        return "updatePersonFirstName";
    }

    @RequestMapping(value = "/update_first_name", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String updatePersonFirstNameByUsername(@ModelAttribute("person") PersonDto personDto) {
        personFacade.updatePersonFirstNameByUsername(httpServletRequest.getUserPrincipal().getName(),
                personDto.getFirstName());
        log.info("Customer is updated: {}", httpServletRequest.getUserPrincipal().getName());
        return "updatePersonFirstNameSuccess";
    }

    @RequestMapping(value = "/update_last_name", method = RequestMethod.GET)
    public String updatePersonLastNameByUsernameView(Model model) {
        model.addAttribute("person", new PersonDto());
        return "updatePersonLastName";
    }

    @RequestMapping(value = "/update_last_name", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String updatePersonLastNameByUsername(@ModelAttribute("person") PersonDto personDto) {
        personFacade.updatePersonLastNameByUsername(httpServletRequest.getUserPrincipal().getName(),
                personDto.getLastName());
        log.info("Customer is updated: {}", httpServletRequest.getUserPrincipal().getName());
        return "updatePersonLastNameSuccess";
    }

    @RequestMapping(value = "/update_phone_number", method = RequestMethod.GET)
    public String updatePersonPhoneNumberByUsernameView(Model model) {
        model.addAttribute("person", new PersonDto());
        return "updatePersonPhoneNumber";
    }

    @RequestMapping(value = "/update_phone_number", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String updatePersonPhoneNumberByUsername(@ModelAttribute("person") PersonDto personDto) {
        personFacade.updatePersonPhoneNumberByUsername(httpServletRequest.getUserPrincipal().getName(),
                personDto.getPhoneNumber());
        log.info("Customer is updated: {}", httpServletRequest.getUserPrincipal().getName());
        return "updatePersonPhoneNumberSuccess";
    }
}
