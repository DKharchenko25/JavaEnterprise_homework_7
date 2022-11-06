package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Role;
import com.dkharchenko_hillel.homework7.reposiroties.PersonRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final EmailService emailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersonServiceImpl(PersonRepository personRepository, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void addPerson(@NonNull String firstName, @NonNull String lastName, @NonNull String email,
                          @NonNull String phoneNumber, @NonNull String username, @NonNull String password) {
        Person newPerson = new Person();
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        newPerson.setEmail(email);
        newPerson.setPhoneNumber(phoneNumber);
        newPerson.setUsername(username);
        newPerson.setPassword(bCryptPasswordEncoder.encode(password));
        if (newPerson.getUsername().contains("admin")) {
            newPerson.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        } else {
            newPerson.setRoles(Collections.singleton(new Role(1L, "ROLE_CUSTOMER")));
        }
        personRepository.save(newPerson);
        emailService.sendRegistrationEmail(newPerson);
    }

    @Override
    public void removePersonById(@NonNull Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            log.error("Person with ID #" + id + " is not found");
            throw new NotFoundException("Person with ID #" + id + " is not found");
        }
    }

    @Override
    public Person getPersonByUsername(@NonNull String username) {
        if (personRepository.findPersonByUsername(username) != null) {
            return personRepository.findPersonByUsername(username);
        } else {
            log.error("Person with username" + username + " is not found");
            throw new NotFoundException("Person with username" + username + " is not found");
        }
    }

    @Override
    public Person getPersonById(@NonNull Long id) {
        if (personRepository.findById(id).isPresent()) {
            return personRepository.findById(id).get();
        } else {
            log.error("Person with ID #" + id + " is not found");
            throw new NotFoundException("Person with ID #" + id + " is not found");
        }
    }

    @Override
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public void updatePersonFirstNameByUsername(@NonNull String username, @NonNull String firstName) {
        if (personRepository.findPersonByUsername(username) != null) {
            personRepository.updatePersonFirstNameByUsername(username, firstName);
        } else {
            log.error("Person with username" + username + " is not found");
            throw new NotFoundException("Person with username" + username + " is not found");
        }
    }

    @Override
    public void updatePersonLastNameByUsername(@NonNull String username, @NonNull String lastName) {
        if (personRepository.findPersonByUsername(username) != null) {
            personRepository.updatePersonLastNameByUsername(username, lastName);
        } else {
            log.error("Person with username" + username + " is not found");
            throw new NotFoundException("Person with username" + username + " is not found");
        }
    }

    @Override
    public void updatePersonEmailByUsername(@NonNull String username, @NonNull String email) {
        if (personRepository.findPersonByUsername(username) != null) {
            personRepository.updatePersonEmailByUsername(username, email);
        } else {
            log.error("Person with username" + username + " is not found");
            throw new NotFoundException("Person with username" + username + " is not found");
        }
    }

    @Override
    public void updatePersonPhoneNumberByUsername(@NonNull String username, @NonNull String phoneNumber) {
        if (personRepository.findPersonByUsername(username) != null) {
            personRepository.updatePersonPhoneNumberByUsername(username, phoneNumber);
        } else {
            log.error("Person with username" + username + " is not found");
            throw new NotFoundException("Person with username" + username + " is not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Person person = personRepository.findPersonByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        return person;
    }
}

