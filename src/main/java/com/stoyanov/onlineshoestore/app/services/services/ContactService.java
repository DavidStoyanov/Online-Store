package com.stoyanov.onlineshoestore.app.services.services;

import com.stoyanov.onlineshoestore.app.models.service.contact.SendEmailServiceModel;

public interface ContactService {

    void sendMsg(SendEmailServiceModel serviceModel);
}
