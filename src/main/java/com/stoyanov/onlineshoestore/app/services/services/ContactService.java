package com.stoyanov.onlineshoestore.app.services.services;

import com.stoyanov.onlineshoestore.app.models.services.contact.SendEmailServiceModel;

public interface ContactService {

    void sendMsg(SendEmailServiceModel serviceModel);
}
