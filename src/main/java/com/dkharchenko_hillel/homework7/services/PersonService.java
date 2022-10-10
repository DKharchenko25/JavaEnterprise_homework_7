package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.models.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends UserDetailsService {
    PersonDto addPerson(PersonDto dto);

    PersonDto removePersonById(Long id);

    Person getPersonByUsername(String username);

    PersonDto getPersonById(Long id);

    List<PersonDto> getAllPersons();

    String updatePersonFirstNameByUsername(String username, PersonDto dto);

    String updatePersonLastNameByUsername(String username, PersonDto dto);

    String updatePersonPhoneNumberByUsername(String username, PersonDto dto);
}
