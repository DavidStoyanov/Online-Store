package com.stoyanov.onlineshoestore.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "home/index.html";
    }

    @GetMapping("/home")
    public String home() {
        return "";
    }
}
