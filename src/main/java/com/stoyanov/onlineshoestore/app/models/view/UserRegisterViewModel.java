package com.stoyanov.onlineshoestore.app.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterViewModel {

    @Size(min = 3, max = 20)
    @Pattern(regexp = "^\\s*[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*\\s*$")
    private String username;

    @Size(min = 8, max = 256)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_.,<>?!@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    private String email;
}
