package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.stoyanov.onlineshoestore.app.errors.user.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.RoleRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.services.RoleService;
import com.stoyanov.onlineshoestore.app.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           RoleService roleService,
                           BCryptPasswordEncoder passwordEncoder,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void register(UserRegisterServiceModel serviceModel) throws UserAlreadyExist {
        Optional<User> optionalUser = this.userRepository.findByUsername(serviceModel.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExist();
        }

        User user = this.mapper.map(serviceModel, User.class);

        //TODO: Configuring Flyway
        if (this.roleRepository.count() == 0) {
            this.roleService.seedRolesInDb();
            user.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        } else {
            user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("USER"))));
        }

        user.setPassword(this.passwordEncoder.encode(serviceModel.getPassword()));

        this.userRepository.saveAndFlush(user);
    }
}
