package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Role;
import com.dkharchenko_hillel.homework7.reposiroties.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dkharchenko_hillel.homework7.converters.PersonConverter.convertPersonToPersonDto;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PersonDto addPerson(PersonDto dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setUsername(dto.getUsername());
        person.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        if (dto.getUsername().contains("admin")) {
            person.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        } else {
            person.setRoles(Collections.singleton(new Role(1L, "ROLE_CUSTOMER")));
        }
        return convertPersonToPersonDto(personRepository.save(person));
    }

    @Override
    public PersonDto removePersonById(Long id) {
        if (personRepository.existsById(id)) {
            PersonDto personDto = convertPersonToPersonDto(personRepository.findById(id).get());
            personRepository.deleteById(id);
            return personDto;
        }
        try {
            throw new NotFoundException("Person with ID #" + id + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Person getPersonByUsername(String username) {
        if (personRepository.findPersonByUsername(username) != null) {
            return personRepository.findPersonByUsername(username);
        }
        try {
            throw new NotFoundException("Person with username " + username + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public PersonDto getPersonById(Long id) {
        if (personRepository.findById(id).isPresent()) {
            return convertPersonToPersonDto(personRepository.findById(id).get());
        }
        try {
            throw new NotFoundException("Person with ID #" + id + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<PersonDto> getAllPersons() {
        List<PersonDto> personDtoList = new ArrayList<>();
        personRepository.findAll().forEach(person -> personDtoList.add(convertPersonToPersonDto(person)));
        return personDtoList;
    }

    @Override
    public String updatePersonFirstNameByUsername(String username, PersonDto dto) {
        if (getPersonByUsername(username) != null) {
            personRepository.updatePersonFirstNameByUsername(username, dto.getFirstName());
            return getPersonByUsername(username).getUsername();
        }
        try {
            throw new NotFoundException("Person with username " + username + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String updatePersonLastNameByUsername(String username, PersonDto dto) {
        if (getPersonByUsername(username) != null) {
            personRepository.updatePersonLastNameByUsername(username, dto.getLastName());
            return getPersonByUsername(username).getUsername();
        }
        try {
            throw new NotFoundException("Person with username " + username + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String updatePersonPhoneNumberByUsername(String username, PersonDto dto) {
        if (getPersonByUsername(username) != null) {
            personRepository.updatePersonPhoneNumberByUsername(username, dto.getPhoneNumber());
            return getPersonByUsername(username).getUsername();
        }
        try {
            throw new NotFoundException("Person with username " + username + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
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

