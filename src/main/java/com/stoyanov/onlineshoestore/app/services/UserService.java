package com.stoyanov.onlineshoestore.app.services;

import com.stoyanov.onlineshoestore.app.errors.user.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegisterServiceModel serviceModel) throws UserAlreadyExist;
}
