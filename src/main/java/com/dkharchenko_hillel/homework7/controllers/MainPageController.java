package com.dkharchenko_hillel.homework7.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/index", "/main", "/", ""})
public class MainPageController {
    @RequestMapping(method = RequestMethod.GET)
    public String mainIndex() {
        return "mainIndex";
    }
}
