package com.stoyanov.onlineshoestore.app.services;

import com.stoyanov.onlineshoestore.app.models.service.UserLoginServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.UserRegisterServiceModel;

public interface UserService {

    void register(UserRegisterServiceModel userRegisterServiceModel);

    void login(UserLoginServiceModel userLoginServiceModel);
}
