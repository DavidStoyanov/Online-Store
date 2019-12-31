package com.stoyanov.onlineshoestore.app.services.services;

import com.stoyanov.onlineshoestore.app.errors.user.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.service.user.UserDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.user.UserManageServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(UserRegisterServiceModel serviceModel) throws UserAlreadyExist;

    List<UserDetailsServiceModel> getAll();

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    void saveUserRoles(UserManageServiceModel serviceModel) throws IllegalAccessException;
}
