package com.example.validation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    public String home(RedirectAttributes redirectAttributes){
        return "index";
    }

}
