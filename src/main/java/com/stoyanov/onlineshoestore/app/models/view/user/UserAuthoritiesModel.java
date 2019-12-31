package com.stoyanov.onlineshoestore.app.models.view.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthoritiesModel {

    private boolean admin;
    private boolean moderator;
    private boolean user;
}
