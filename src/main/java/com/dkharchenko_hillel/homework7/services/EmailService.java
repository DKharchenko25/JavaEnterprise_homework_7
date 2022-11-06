package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.models.Person;

public interface EmailService {

    void sendRegistrationEmail(Person person);
}
