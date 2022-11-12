package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.models.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends UserDetailsService {
    void addPerson(Person person);

    void removePersonById(Long id);

    Person getPersonByUsername(String username);

    Person getPersonById(Long id);

    List<Person> getAllPersons();

    void updatePersonFirstNameByUsername(String username, String firstName);

    void updatePersonLastNameByUsername(String username, String lastName);

    void updatePersonEmailByUsername(String username, String email);

    void updatePersonPhoneNumberByUsername(String username, String phoneNumber);
}
