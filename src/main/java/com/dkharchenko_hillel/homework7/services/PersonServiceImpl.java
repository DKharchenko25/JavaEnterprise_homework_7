package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Role;
import com.dkharchenko_hillel.homework7.reposiroties.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Person addPerson(String firstName, String lastName, String phoneNumber, String username, String password) {
        Person newPerson = new Person();
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        newPerson.setPhoneNumber(phoneNumber);
        newPerson.setUsername(username);
        newPerson.setPassword(bCryptPasswordEncoder.encode(password));
        if (newPerson.getUsername().contains("admin")) {
            newPerson.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        } else {
            newPerson.setRoles(Collections.singleton(new Role(1L, "ROLE_CUSTOMER")));
        }
        return personRepository.save(newPerson);
    }

    @Override
    public void removePersonById(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            try {
                throw new NotFoundException("Person with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public Person getPersonByUsername(String username) {
        if (personRepository.findPersonByUsername(username) != null) {
            return personRepository.findPersonByUsername(username);
        } else {
            try {
                throw new NotFoundException("Person with username " + username + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public Person getPersonById(Long id) {
        if (personRepository.findById(id).isPresent()) {
            return personRepository.findById(id).get();
        } else {
            try {
                throw new NotFoundException("Person with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public void updatePersonFirstNameByUsername(String username, String firstName) {
        if (getPersonByUsername(username) != null) {
            personRepository.updatePersonFirstNameByUsername(username, firstName);
        } else {
            try {
                throw new NotFoundException("Person with username " + username + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void updatePersonLastNameByUsername(String username, String lastName) {
        if (getPersonByUsername(username) != null) {
            personRepository.updatePersonLastNameByUsername(username, lastName);
        } else {
            try {
                throw new NotFoundException("Person with username " + username + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void updatePersonPhoneNumberByUsername(String username, String phoneNumber) {
        if (getPersonByUsername(username) != null) {
            personRepository.updatePersonPhoneNumberByUsername(username, phoneNumber);
        } else {
            try {
                throw new NotFoundException("Person with username " + username + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = getPersonByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        return person;
    }
}

