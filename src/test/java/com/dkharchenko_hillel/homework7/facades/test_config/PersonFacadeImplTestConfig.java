package com.dkharchenko_hillel.homework7.facades.test_config;

import com.dkharchenko_hillel.homework7.facades.PersonFacade;
import com.dkharchenko_hillel.homework7.facades.PersonFacadeImpl;
import com.dkharchenko_hillel.homework7.services.PersonService;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class PersonFacadeImplTestConfig {

    public PersonFacade personFacade(PersonService personService) {
        return new PersonFacadeImpl(personService);
    }
}
