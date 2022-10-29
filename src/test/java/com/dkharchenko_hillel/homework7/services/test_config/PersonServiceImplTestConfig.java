package com.dkharchenko_hillel.homework7.services.test_config;

import com.dkharchenko_hillel.homework7.reposiroties.PersonRepository;
import com.dkharchenko_hillel.homework7.services.PersonService;
import com.dkharchenko_hillel.homework7.services.PersonServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
public class PersonServiceImplTestConfig {

    @Bean
    public PersonService personService(PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new PersonServiceImpl(personRepository, bCryptPasswordEncoder);
    }
}
