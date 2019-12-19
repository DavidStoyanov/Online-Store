package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.models.service.contact.SendEmailServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.contact.SendEmailViewModel;
import com.stoyanov.onlineshoestore.app.services.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactsController {

    private final ContactService contactService;

    private final ModelMapper mapper;

    public ContactsController(ContactService contactService, ModelMapper mapper) {
        this.contactService = contactService;
        this.mapper = mapper;
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts/contacts.html";
    }

    @PostMapping("/contacts")
    public String contactsConfirm(@ModelAttribute SendEmailViewModel viewModel) {
        SendEmailServiceModel serviceModel = this.mapper.map(viewModel, SendEmailServiceModel.class);
        this.contactService.sendMsg(serviceModel);
        return "redirect:/contacts";
    }
}
