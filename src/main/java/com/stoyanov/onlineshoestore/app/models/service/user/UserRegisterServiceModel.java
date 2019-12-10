package com.stoyanov.onlineshoestore.app.models.service.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterServiceModel {

    private String username;
    private String password;
    private String email;

    public void trimUsername() {
        this.username = this.username.trim();
    }
}
