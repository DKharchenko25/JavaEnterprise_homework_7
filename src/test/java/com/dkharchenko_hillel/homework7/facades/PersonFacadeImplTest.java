package com.dkharchenko_hillel.homework7.facades;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.converters.PersonConverter;
import com.dkharchenko_hillel.homework7.dtos.PersonDto;
import com.dkharchenko_hillel.homework7.facades.test_config.PersonFacadeImplTestConfig;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonFacadeImplTestConfig.class)
class PersonFacadeImplTest {

    @Autowired
    private PersonFacade personFacade;

    @MockBean
    private PersonService personService;

    @Test
    void addPersonSuccess() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("success");
        personDto.setLastName("success");
        personDto.setEmail("success@gmail.com");
        personDto.setPhoneNumber("+38097");
        personDto.setUsername("success");
        personDto.setPassword("success");
        doNothing().when(personService).addPerson(personDto.getFirstName(), personDto.getLastName(), personDto.getEmail(),
                personDto.getPhoneNumber(), personDto.getUsername(), personDto.getPassword());
        personFacade.addPerson(personDto);
        verify(personService, times(1)).addPerson(personDto.getFirstName(), personDto.getLastName(),
                personDto.getEmail(), personDto.getPhoneNumber(), personDto.getUsername(), personDto.getPassword());
    }

    @Test
    void addPersonMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.addPerson(null));
    }

    @ParameterizedTest
    @MethodSource("invalidInputsSource")
    void addPersonMustThrowIllegalArgumentException(String firstName, String lastName, String email, String number) {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName(firstName);
        personDto.setLastName(lastName);
        personDto.setEmail(email);
        personDto.setPhoneNumber(number);
        personDto.setUsername("test");
        personDto.setPassword("test");
        assertThrows(IllegalArgumentException.class, () -> personFacade.addPerson(personDto));
    }

    private static Stream<Arguments> invalidInputsSource() {
        return Stream.of(
                Arguments.of("", "test", "test@gmail.com", "+38097"),
                Arguments.of("test", "", "test@gmail.com", "+38097"),
                Arguments.of("test", "test", "test@gmail.com", "test"),
                Arguments.of("??!!", "test", "test@gmail.com", "+38097"),
                Arguments.of("test", "--__", "test@gmail.com", "+38097"),
                Arguments.of("test", "test", "test@gmail.com", ""),
                Arguments.of("test", "test", "test", "+38097"));

    }

    @Test
    void removePersonSuccess() {
        doNothing().when(personService).removePersonById(1L);
        personFacade.removePerson(1L);
        verify(personService, times(1)).removePersonById(1L);
    }

    @Test
    void removePersonMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.removePerson(null));
    }

    @Test
    void removePersonMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(personService).removePersonById(1L);
        assertThrows(NotFoundException.class, () -> personFacade.removePerson(1L));
    }

    @Test
    void getPersonByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personService.getPersonByUsername(person.getUsername())).thenReturn(person);
        assertEquals(PersonConverter.convertPersonToPersonDto(person).getUsername(),
                personFacade.getPersonByUsername(person.getUsername()).getUsername());
    }


    @Test
    void getPersonByUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.getPersonByUsername(null));
    }

    @Test
    void getPersonByUsernameMustThrowNotFoundException() {
        doThrow(NotFoundException.class).when(personService).getPersonByUsername("success");
        assertThrows(NotFoundException.class, () -> personFacade.getPersonByUsername("success"));
    }

    @Test
    void getAllPersons() {
        List<Person> personList = new ArrayList<>();
        when(personService.getAllPersons()).thenReturn(personList);
        assertEquals(personList.stream().map(PersonConverter::convertPersonToPersonDto).collect(Collectors.toList()),
                personFacade.getAllPersons());
    }

    @Test
    void updatePersonFirstNameByUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonFirstNameByUsername(null, "fail"));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonFirstNameByUsername("fail", null));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonFirstNameByUsername(null, null));
    }

    @Test
    void updatePersonFirstNameSuccess() {
        doNothing().when(personService).updatePersonFirstNameByUsername("success", "success");
        personFacade.updatePersonFirstNameByUsername("success", "success");
        verify(personService, times(1)).updatePersonFirstNameByUsername("success", "success");
    }

    @ParameterizedTest
    @MethodSource("invalidNameSource")
    void updatePersonFirstNameByUsernameMustThrowIllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> personFacade.updatePersonFirstNameByUsername("fail", name));

        doThrow(NotFoundException.class).when(personService).updatePersonFirstNameByUsername("fail", "fail");
        assertThrows(NotFoundException.class, () -> personFacade.updatePersonFirstNameByUsername("fail", "fail"));
    }

    private static Stream<String> invalidNameSource() {
        return Stream.of("", " ", "??!", "--__");
    }

    @Test
    void updatePersonLastNameSuccess() {
        doNothing().when(personService).updatePersonLastNameByUsername("success", "success");
        personFacade.updatePersonLastNameByUsername("success", "success");
        verify(personService, times(1)).updatePersonLastNameByUsername("success", "success");
    }

    @ParameterizedTest
    @MethodSource("invalidNameSource")
    void updatePersonLastNameByUsernameMustThrowIllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> personFacade.updatePersonLastNameByUsername("fail", name));

        doThrow(NotFoundException.class).when(personService).updatePersonLastNameByUsername("fail", "fail");
        assertThrows(NotFoundException.class, () -> personFacade.updatePersonLastNameByUsername("fail", "fail"));
    }

    @Test
    void updatePersonLastNameByUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonLastNameByUsername(null, "fail"));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonLastNameByUsername("fail", null));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonLastNameByUsername(null, null));
    }

    @Test
    void updatePersonEmailSuccess() {
        doNothing().when(personService).updatePersonEmailByUsername("success", "test@gmail.com");
        personFacade.updatePersonEmailByUsername("success", "test@gmail.com");
        verify(personService, times(1)).updatePersonEmailByUsername("success", "test@gmail.com");
    }

    @ParameterizedTest
    @MethodSource("invalidNameSource")
    void updatePersonEmailByUsernameMustThrowIllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> personFacade.updatePersonEmailByUsername("fail", name));

        doThrow(NotFoundException.class).when(personService).updatePersonEmailByUsername("fail", "fail@gmail.com");
        assertThrows(NotFoundException.class, () -> personFacade.updatePersonEmailByUsername("fail", "fail@gmail.com"));
    }

    @Test
    void updatePersonEmailByUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonEmailByUsername(null, "fail@gmail.com"));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonEmailByUsername("fail", null));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonEmailByUsername(null, null));
    }

    @Test
    void updatePersonPhoneNumberSuccess() {
        doNothing().when(personService).updatePersonPhoneNumberByUsername("success", "+38097");
        personFacade.updatePersonPhoneNumberByUsername("success", "+38097");
        verify(personService, times(1)).updatePersonPhoneNumberByUsername("success", "+38097");
    }

    @ParameterizedTest
    @MethodSource("invalidNameSource")
    void updatePersonPhoneNumberByUsernameMustThrowIllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> personFacade.updatePersonPhoneNumberByUsername("fail", name));

        doThrow(NotFoundException.class).when(personService).updatePersonPhoneNumberByUsername("fail", "+38097");
        assertThrows(NotFoundException.class, () -> personFacade.updatePersonPhoneNumberByUsername("fail", "+38097"));
    }

    @Test
    void updatePersonPhoneNumberByUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonPhoneNumberByUsername(null, "+38097"));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonPhoneNumberByUsername("fail", null));
        assertThrows(NullPointerException.class, () -> personFacade.updatePersonPhoneNumberByUsername(null, null));
    }

}