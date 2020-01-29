package com.stoyanov.onlineshoestore.app.models.services.user;

import com.stoyanov.onlineshoestore.app.models.views.user.UserAuthoritiesModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserManageServiceModel {

    private String username;
    private UserAuthoritiesModel authorities;
}