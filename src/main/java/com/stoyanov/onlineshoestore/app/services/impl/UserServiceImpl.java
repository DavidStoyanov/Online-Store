package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.models.entity.User;
import com.stoyanov.onlineshoestore.app.models.service.UserLoginServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void register(UserRegisterServiceModel userRegisterServiceModel) {
        userRegisterServiceModel.trimUsername();
        User user = this.mapper.map(userRegisterServiceModel, User.class);
        this.userRepository.save(user);
    }

    @Override
    public void login(UserLoginServiceModel userLoginServiceModel) {

    }
}
