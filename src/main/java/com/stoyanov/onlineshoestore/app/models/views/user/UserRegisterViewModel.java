package com.stoyanov.onlineshoestore.app.models.views.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterViewModel {

    private static final String USERNAME_ERROR_MESSAGE = "The username should be between 3 and 20 characters long, and contain only alphabetic characters [ _-]";
    private static final String PASSWORD_SIZE_ERROR_MESSAGE = "Password should be at least 8 characters";
    private static final String PASSWORD_ERROR_MESSAGE = "Password should contains one lower and upper case, 1 numeric character, 1 special character";
    private static final String EMAIL_ERROR_MESSAGE = "Invalid e-mail";

    @Size(min = 3, max = 20, message = USERNAME_ERROR_MESSAGE)
    @Pattern(regexp = "^\\s*[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*\\s*$", message = USERNAME_ERROR_MESSAGE)
    private String username;

    @Size(min = 8, max = 255, message = PASSWORD_SIZE_ERROR_MESSAGE)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_.,<>?!@#$%^&+=])(?=\\S+$).{8,}$",message = PASSWORD_ERROR_MESSAGE)
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = EMAIL_ERROR_MESSAGE)
    private String email;
}
