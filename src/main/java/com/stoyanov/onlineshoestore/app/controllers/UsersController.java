package com.stoyanov.onlineshoestore.app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/manage")
    public String getUsers() {
        return "user/manage.html";
    }
}
