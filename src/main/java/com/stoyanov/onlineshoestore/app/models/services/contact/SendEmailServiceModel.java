package com.stoyanov.onlineshoestore.app.models.services.contact;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendEmailServiceModel {

    private String fullName;
    private String email;
    private String message;
}
