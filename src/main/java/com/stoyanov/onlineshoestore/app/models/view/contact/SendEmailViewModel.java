package com.stoyanov.onlineshoestore.app.models.view.contact;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendEmailViewModel {

    private String fullName;
    private String email;
    private String message;
}
