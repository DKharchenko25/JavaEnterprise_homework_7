package com.dkharchenko_hillel.homework7.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionsController implements ErrorController {
    private final static String PATH = "/error";

    @RequestMapping(PATH)
    @ResponseBody
    public String getErrorPath() {
        return "Sorry, something went wrong. Possible issues:\n " +
                "1. Access denied;\n" +
                "2. Wrong ID was used in input field;\n" +
                "3. Invalid input data was used in input field.";
    }
}

