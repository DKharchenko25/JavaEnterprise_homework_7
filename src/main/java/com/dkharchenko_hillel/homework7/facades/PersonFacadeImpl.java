package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.converters.PersonConverter;
import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.services.PersonService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.dkharchenko_hillel.homework7.converters.PersonConverter.convertPersonDtoToPerson;
import static com.dkharchenko_hillel.homework7.converters.PersonConverter.convertPersonToPersonDto;
import static com.dkharchenko_hillel.homework7.validators.InputValidator.*;

@Slf4j
@Component
public class PersonFacadeImpl implements PersonFacade {

    private final PersonService personService;

    public PersonFacadeImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void addPerson(@NonNull PersonDto dto) {
        dto.setFirstName(checkName(dto.getFirstName()));
        dto.setLastName(checkName(dto.getLastName()));
        dto.setEmail(checkEmail(dto.getEmail()));
        dto.setPhoneNumber(checkNumber(dto.getPhoneNumber()));
        personService.addPerson(convertPersonDtoToPerson(dto));
    }

    @Override
    public void removePerson(@NonNull Long id) {
        personService.removePersonById(id);
    }

    @Override
    public PersonDto getPersonByUsername(@NonNull String username) {
        return convertPersonToPersonDto(personService.getPersonByUsername(username));
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons().stream().map(PersonConverter::convertPersonToPersonDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePersonFirstNameByUsername(@NonNull String username, @NonNull String firstName) {
        personService.updatePersonFirstNameByUsername(username, checkName(firstName));
    }

    @Override
    public void updatePersonLastNameByUsername(@NonNull String username, @NonNull String lastName) {
        personService.updatePersonLastNameByUsername(username, checkName(lastName));
    }

    @Override
    public void updatePersonEmailByUsername(@NonNull String username, @NonNull String email) {
        personService.updatePersonEmailByUsername(username, checkEmail(email));
    }

    @Override
    public void updatePersonPhoneNumberByUsername(@NonNull String username, @NonNull String phoneNumber) {
        personService.updatePersonPhoneNumberByUsername(username, checkNumber(phoneNumber));
    }

}
