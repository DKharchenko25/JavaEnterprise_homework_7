package com.dkharchenko_hillel.homework7.facades;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InputValidator {

    public static String checkName(String name) {
        if (name.matches("[A-Za-zА-Яа-я\\d\\-]+")) {
            return name;
        } else {
            log.error("Name is invalid: {}", name);
            throw new IllegalArgumentException("Invalid name");
        }
    }

    public static String checkEmail(String email) {
        if (email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]" +
                "+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            return email;
        } else {
            log.error("Email is invalid: {}", email);
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public static String checkNumber(String number) {
        if (number.matches("[\\d+]+")) {
            return number;
        } else {
            log.error("Number is invalid: {}", number);
            throw new IllegalArgumentException("Invalid number");
        }
    }
}
