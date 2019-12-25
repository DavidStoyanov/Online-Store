package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.stoyanov.onlineshoestore.app.models.entity.user.Role;
import com.stoyanov.onlineshoestore.app.repositories.RoleRepository;
import com.stoyanov.onlineshoestore.app.services.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDb() {
        this.roleRepository.saveAndFlush(new Role("ROOT"));
        this.roleRepository.saveAndFlush(new Role("ADMIN"));
        this.roleRepository.saveAndFlush(new Role("MODERATOR"));
        this.roleRepository.saveAndFlush(new Role("USER"));
    }
}
