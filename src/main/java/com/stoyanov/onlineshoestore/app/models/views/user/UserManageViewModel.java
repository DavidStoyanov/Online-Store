package com.stoyanov.onlineshoestore.app.models.views.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserManageViewModel {

    private String username;
    private UserAuthoritiesModel authorities;
}
