package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.converters.PersonConverter;
import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PersonController {
    private final PersonService personService;

    private final HttpServletRequest httpServletRequest;


    public PersonController(PersonService personService, HttpServletRequest httpServletRequest) {
        this.personService = personService;
        this.httpServletRequest = httpServletRequest;
    }


    @RequestMapping(value = "/add_person", method = RequestMethod.GET)
    public String addPersonView(Model model) {
        model.addAttribute("person", new PersonDto());
        return "addPerson";
    }

    @RequestMapping(value = "/add_person", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("person") PersonDto personDto) {
        personService.addPerson(personDto.getFirstName(), personDto.getLastName(), personDto.getPhoneNumber(),
                personDto.getUsername(), personDto.getPassword());
        log.info("New customer is added to persons table: {}", personDto.getUsername());
        return "redirect:/all_persons";
    }

    @RequestMapping(value = "/remove_person", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removePersonById(@RequestParam Long id) {
        personService.removePersonById(id);
        log.info("Customer is removed from persons table: {}", id);
        return "redirect:/all_persons";
    }

    @GetMapping("/all_persons")
    public String getAllPersons(Model model) {
        model.addAttribute("all", personService.getAllPersons().stream()
                .map(PersonConverter::convertPersonToPersonDto).collect(Collectors.toList()));
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
        personService.updatePersonFirstNameByUsername(httpServletRequest.getUserPrincipal().getName(),
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
        personService.updatePersonLastNameByUsername(httpServletRequest.getUserPrincipal().getName(),
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
        personService.updatePersonPhoneNumberByUsername(httpServletRequest.getUserPrincipal().getName(),
                personDto.getPhoneNumber());
        log.info("Customer is updated: {}", httpServletRequest.getUserPrincipal().getName());
        return "updatePersonPhoneNumberSuccess";
    }
}
