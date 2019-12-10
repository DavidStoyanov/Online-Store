package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.errors.BadLoginArgsException;
import com.stoyanov.onlineshoestore.app.errors.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.service.user.UserLoginServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.user.UserSessionModel;
import com.stoyanov.onlineshoestore.app.models.view.user.UserLoginViewModel;
import com.stoyanov.onlineshoestore.app.models.view.user.UserRegisterViewModel;
import com.stoyanov.onlineshoestore.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

        try {
            this.userService.register(serviceModel);
        } catch (UserAlreadyExist exception) {
            bindingResult.rejectValue("username", "error.registerModel", exception.getMessage());
            return "user/register.html";
        }

        return "redirect:/user/login";
    }


    @ModelAttribute("loginModel")
    public UserLoginViewModel loginModel() {
        return new UserLoginViewModel();
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("loginModel") UserLoginViewModel loginModel) {
        return "user/login.html";
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@Valid @ModelAttribute("loginModel") UserLoginViewModel viewModel,
                               BindingResult bindingResult,
                               ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/login.html");
        }

        UserLoginServiceModel serviceModel = this.mapper.map(viewModel, UserLoginServiceModel.class);

        try {
            UserSessionModel userModel = this.userService.login(serviceModel);
            mav.addObject("user", userModel);
            mav.setViewName("redirect:/home");
            return mav;
        } catch (BadLoginArgsException exception) {
            bindingResult.rejectValue("password", "error.loginModel", exception.getMessage());
            mav.setViewName("user/login.html");
        }

        return mav;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
