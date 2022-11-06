package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.reposiroties.PersonRepository;
import com.dkharchenko_hillel.homework7.services.test_config.PersonServiceImplTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonServiceImplTestConfig.class)
class PersonServiceImplTest {
    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private EmailService emailService;
    @Autowired
    private PersonService personService;

    @Test
    void addPersonSuccess() {
        Person person = new Person();
        person.setFirstName("test");
        person.setLastName("test");
        person.setEmail("test@gmail.com");
        person.setPhoneNumber("+38093");
        person.setUsername("test");
        person.setPassword("test");
        when(personRepository.save(person)).thenReturn(person);
        personService.addPerson(person.getFirstName(), person.getLastName(), person.getEmail(), person.getPhoneNumber(),
                person.getUsername(), person.getPassword());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @ParameterizedTest
    @MethodSource("invalidInputsSource")
    void addPersonMustThrowNullPointerException(String firstName, String lastName, String email, String phoneNumber,
                                                String username, String password) {
        assertThrows(NullPointerException.class, () -> personService.addPerson(firstName, lastName, email,
                phoneNumber, username, password));
    }

    private static Stream<Arguments> invalidInputsSource() {
        return Stream.of(Arguments.of(null, "test", "test@gmail.com",
                "2464", "test", "test"), Arguments.of("test", null, "test@gmail.com",
                "2464", "test", "test"), Arguments.of("test", "test", "test@gmail.com",
                null, "test", "test"), Arguments.of("test", "test", "test@gmail.com",
                "2464", null, "test"), Arguments.of("test", "test", "test@gmail.com",
                "2464", "test", null), Arguments.of("test", "test", null,
                "2464", "test", "test"));
    }

    @Test
    void removePersonByIdSuccess() {
        when(personRepository.existsById(2L)).thenReturn(true);
        personService.removePersonById(2L);
        verify(personRepository, times(1)).deleteById(2L);
    }

    @Test
    void removePersonMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.removePersonById(null));
    }

    @Test
    void removePersonMustThrowNotFoundException() {
        when(personRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> personService.removePersonById(1L));
    }

    @Test
    void getPersonByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("Tom");
        when(personRepository.findPersonByUsername(person.getUsername())).thenReturn(person);
        assertEquals(person, personService.getPersonByUsername("Tom"));
    }

    @Test
    void getPersonByUsernameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.getPersonByUsername(null));
    }

    @Test
    void getPersonByUsernameMustThrowNotFoundException() {
        when(personRepository.findPersonByUsername("test")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> personService.getPersonByUsername("test"));
    }

    @Test
    void getPersonByIdSuccess() {
        Person person = new Person();
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        assertEquals(person, personService.getPersonById(1L));
    }

    @Test
    void getPersonByIdMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.getPersonById(null));
    }

    @Test
    void getPersonByUIdMustThrowNotFoundException() {
        when(personRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> personService.getPersonById(2L));
    }

    @Test
    void getAllPersonsSuccess() {
        List<Person> personList = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(personList);
        assertEquals(personList, personService.getAllPersons());
    }

    @Test
    void updatePersonFirstNameByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personRepository.findPersonByUsername("success")).thenReturn(person);
        personService.updatePersonFirstNameByUsername("success", "test");
        verify(personRepository, times(1)).updatePersonFirstNameByUsername("success", "test");
    }

    @Test
    void updatePersonFirstNameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.updatePersonFirstNameByUsername("test", null));
        assertThrows(NullPointerException.class, () -> personService.updatePersonFirstNameByUsername(null, "test"));
        assertThrows(NullPointerException.class, () -> personService.updatePersonFirstNameByUsername(null, null));
    }

    @Test
    void updatePersonFirstNameMustThrowNotFoundException() {
        when(personRepository.findPersonByUsername("test")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> personService.updatePersonFirstNameByUsername("test", "Tom"));
    }

    @Test
    void updatePersonLastNameByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personRepository.findPersonByUsername("success")).thenReturn(person);
        personService.updatePersonLastNameByUsername("success", "test");
        verify(personRepository, times(1)).updatePersonLastNameByUsername("success", "test");
    }

    @Test
    void updatePersonLastNameMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.updatePersonLastNameByUsername("test", null));
        assertThrows(NullPointerException.class, () -> personService.updatePersonLastNameByUsername(null, "test"));
        assertThrows(NullPointerException.class, () -> personService.updatePersonLastNameByUsername(null, null));
    }

    @Test
    void updatePersonLastNameMustThrowNotFoundException() {
        when(personRepository.findPersonByUsername("test")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> personService.updatePersonLastNameByUsername("test", "Tom"));
    }

    @Test
    void updatePersonEmailByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personRepository.findPersonByUsername("success")).thenReturn(person);
        personService.updatePersonEmailByUsername("success", "test");
        verify(personRepository, times(1)).updatePersonEmailByUsername("success", "test");
    }

    @Test
    void updatePersonEmailMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.updatePersonEmailByUsername("test", null));
        assertThrows(NullPointerException.class, () -> personService.updatePersonEmailByUsername(null, "test"));
        assertThrows(NullPointerException.class, () -> personService.updatePersonEmailByUsername(null, null));
    }

    @Test
    void updatePersonEmailMustThrowNotFoundException() {
        when(personRepository.findPersonByUsername("test")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> personService.updatePersonEmailByUsername("test", "test@gmail.com"));
    }

    @Test
    void updatePersonPhoneNumberByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personRepository.findPersonByUsername("success")).thenReturn(person);
        personService.updatePersonPhoneNumberByUsername("success", "+380678");
        verify(personRepository, times(1)).updatePersonPhoneNumberByUsername("success", "+380678");
    }

    @Test
    void updatePersonPhoneNumberMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.updatePersonPhoneNumberByUsername("test", null));
        assertThrows(NullPointerException.class, () -> personService.updatePersonPhoneNumberByUsername(null, "+38093"));
        assertThrows(NullPointerException.class, () -> personService.updatePersonPhoneNumberByUsername(null, null));
    }

    @Test
    void updatePersonPhoneNumberMustThrowNotFoundException() {
        when(personRepository.findPersonByUsername("test")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> personService.updatePersonPhoneNumberByUsername("test", "+309875"));
    }

    @Test
    void loadUserByUsernameSuccess() {
        Person person = new Person();
        person.setUsername("success");
        when(personRepository.findPersonByUsername("success")).thenReturn(person);
        assertEquals(person, personService.loadUserByUsername("success"));
    }

    @Test
    void loadUserMustThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> personService.loadUserByUsername(null));
    }

    @Test
    void loadUserMustThrowUserNotFoundException() {
        when(personRepository.findPersonByUsername("test")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> personService.loadUserByUsername("test"));
    }
}