package com.stoyanov.onlineshoestore.app.services.services;

import com.stoyanov.onlineshoestore.app.errors.user.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.UserService;
import com.stoyanov.onlineshoestore.app.services.base.ServiceTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends ServiceTestBase {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService service;

    @Test
    void register_whenUsernameExists_shouldThrowUserAlreadyExist() {
        String username = "username";
        UserRegisterServiceModel registerModel = new UserRegisterServiceModel();
        registerModel.setUsername(username);

        Mockito.when(userRepository.findByUsername(registerModel.getUsername()))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThrows(UserAlreadyExist.class,
                () -> service.register(registerModel));
    }
}