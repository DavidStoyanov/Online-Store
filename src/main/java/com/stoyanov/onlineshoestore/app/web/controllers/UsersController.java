package com.stoyanov.onlineshoestore.app.web.controllers;

import com.stoyanov.onlineshoestore.app.models.services.user.UserManageServiceModel;
import com.stoyanov.onlineshoestore.app.models.views.user.UserAuthoritiesModel;
import com.stoyanov.onlineshoestore.app.models.views.user.UserDetailsViewModel;
import com.stoyanov.onlineshoestore.app.models.views.user.UserManageViewModel;
import com.stoyanov.onlineshoestore.app.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    private final ModelMapper mapper;

    @Autowired
    public UsersController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/manage")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    public ModelAndView getUsers(ModelAndView mav) {
        List<UserDetailsViewModel> users = this.userService.getAll().stream()
                .map(user -> this.mapper.map(user, UserDetailsViewModel.class))
                .collect(Collectors.toList());
        mav.addObject("users", users);
        mav.setViewName("user/manage.html");
        return mav;
    }

    @PostMapping("/manage")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
    public String manageUserConfirm(@ModelAttribute UserManageViewModel viewModel) throws IllegalAccessException {
        UserManageServiceModel serviceModel = this.mapper.map(viewModel, UserManageServiceModel.class);
        if (serviceModel.getAuthorities() == null) {
            serviceModel.setAuthorities(new UserAuthoritiesModel());
        }

        this.userService.saveUserRoles(serviceModel);

        return "redirect:/users/manage";
    }
}
