package com.example.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/calend")
public class CalendarController {

    @GetMapping
    public ModelAndView calend(){
        return new ModelAndView("calend");
    }
}
