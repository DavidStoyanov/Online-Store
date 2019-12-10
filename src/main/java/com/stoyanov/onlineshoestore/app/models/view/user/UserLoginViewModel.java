package com.stoyanov.onlineshoestore.app.models.view.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginViewModel {

    @NotNull(message = "username cant be empty")
    private String username;

    @NotNull(message = "password cant be empty")
    private String password;
}
