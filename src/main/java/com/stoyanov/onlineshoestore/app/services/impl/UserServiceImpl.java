package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.errors.BadLoginArgsException;
import com.stoyanov.onlineshoestore.app.errors.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.user.UserLoginServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.user.UserSessionModel;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.HashingService;
import com.stoyanov.onlineshoestore.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private final HashingService hashingService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, HashingService hashingService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.hashingService = hashingService;
    }

    @Override
    public void register(UserRegisterServiceModel serviceModel) throws UserAlreadyExist {
        Optional<User> optionalUser = this.userRepository.findByUsername(serviceModel.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExist("User already exist");
        }

        serviceModel.trimUsername();
        serviceModel.setPassword(this.hashingService.hash(serviceModel.getPassword()));

        User user = this.mapper.map(serviceModel, User.class);
        this.userRepository.save(user);
    }

    @Override
    public UserSessionModel login(UserLoginServiceModel serviceModel) throws BadLoginArgsException {
        Optional<User> optionalUser = this.userRepository.findByUsername(serviceModel.getUsername());
        if (optionalUser.isEmpty()) {
            throw new BadLoginArgsException("Invalid username or password");
        }

        User user = optionalUser.get();

        String password = this.hashingService.hash(serviceModel.getPassword());
        if (!user.getPassword().equals(password)) {
            throw new BadLoginArgsException("Invalid username or password");
        }

        return this.mapper.map(user, UserSessionModel.class);
    }
}
