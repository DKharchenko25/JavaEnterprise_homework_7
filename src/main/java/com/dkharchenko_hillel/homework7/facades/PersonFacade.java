package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.dtos.PersonDto;

import java.util.List;

public interface PersonFacade {

    void addPerson(PersonDto dto);

    void removePerson(Long id);

    PersonDto getPersonByUsername(String username);

    List<PersonDto> getAllPersons();

    void updatePersonFirstNameByUsername(String username, String firstName);

    void updatePersonLastNameByUsername(String username, String lastName);

    void updatePersonPhoneNumberByUsername(String username, String phoneNumber);
}
