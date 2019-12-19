package com.stoyanov.onlineshoestore.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController {

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts/contacts.html";
    }
}
