package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.stoyanov.onlineshoestore.app.errors.user.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.errors.user.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entities.user.Role;
import com.stoyanov.onlineshoestore.app.models.entities.user.User;
import com.stoyanov.onlineshoestore.app.models.services.user.UserDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.services.user.UserManageServiceModel;
import com.stoyanov.onlineshoestore.app.models.services.user.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.RoleRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.services.RoleService;
import com.stoyanov.onlineshoestore.app.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<UserDetailsServiceModel> getAll() {
        return this.userRepository.findAll().stream()
                .filter(u -> {
                    boolean isRoot = false;
                    for (Role authority : u.getAuthorities()) {
                        if (authority.getAuthority().equals("ROOT")) {
                            isRoot = true;
                            break;
                        }
                    }

                    return !isRoot;
                })
                .map(u -> {
                    UserDetailsServiceModel serviceModel = this.mapper.map(u, UserDetailsServiceModel.class);
                    List<String> collect = this.getRoles(u.getAuthorities());
                    serviceModel.setAuthorities(collect);

                    return serviceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveUserRoles(UserManageServiceModel serviceModel) throws IllegalAccessException {
        User user = this.userRepository.findByUsername(serviceModel.getUsername())
                .orElseThrow(UserNotFoundException::new);

        List<String> userAuthorities = this.getRoles(user.getAuthorities());

        serviceModel.getAuthorities().setUser(true);
        Field[] modelFields = serviceModel.getAuthorities().getClass().getDeclaredFields();
        for (Field modelField : modelFields) {
            modelField.setAccessible(true);
            String authority = modelField.getName().toUpperCase();
            if (!userAuthorities.contains(authority)) {
                if (modelField.getBoolean(serviceModel.getAuthorities())) {
                    //Add role to user, save and flush user
                    Role role = this.roleRepository.findByAuthority(authority);
                    Set<Role> authorities = user.getAuthorities();
                    authorities.add(role);
                    user.setAuthorities(authorities);
                    userRepository.saveAndFlush(user);
                }
            } else {
                if (!modelField.getBoolean(serviceModel.getAuthorities())) {
                    //Admin cant demote admins
                    if (authority.equals("ADMIN")) {
                        List<String> roles = this.getRoles(this.getAuthenticatedUser().getAuthorities());
                        if (!roles.contains("ROOT")) continue;
                    }
                    //Remove role from user, save and flush user
                    Role role = this.roleRepository.findByAuthority(authority);
                    Set<Role> authorities = user.getAuthorities();
                    authorities.remove(role);
                    user.setAuthorities(authorities);
                    userRepository.saveAndFlush(user);
                }
            }
        }
    }


    private List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @PreAuthorize("isAuthenticated()")
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return (User) authentication.getPrincipal();
    }
}
