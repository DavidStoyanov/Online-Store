package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.models.service.UserLoginServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.UserRegisterViewModel;
import com.stoyanov.onlineshoestore.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @ModelAttribute("registerModel")
    public UserRegisterViewModel registerModel() {
        return new UserRegisterViewModel();
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("registerModel") UserRegisterViewModel viewModel) {
        return "user/register.html";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("registerModel") UserRegisterViewModel viewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/register.html";
        }

        UserRegisterServiceModel serviceModel = this.mapper.map(viewModel, UserRegisterServiceModel.class);
        this.userService.register(serviceModel);

        return "redirect:/user/login";
    }


    @ModelAttribute("loginModel")
    public UserLoginServiceModel loginModel() {
        return new UserLoginServiceModel();
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("loginModel") UserLoginServiceModel loginModel) {
        return "user/login.html";
    }

    @PostMapping("/login")
    public void loginConfirm() {

    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
