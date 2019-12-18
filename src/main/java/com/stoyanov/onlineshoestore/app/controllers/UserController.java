package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.errors.user.UserAlreadyExist;
import com.stoyanov.onlineshoestore.app.models.service.user.UserRegisterServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.user.UserRegisterViewModel;
import com.stoyanov.onlineshoestore.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String registerConfirm(@Valid @ModelAttribute("registerModel") UserRegisterViewModel viewModel,
                                  BindingResult bindingResult) {
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

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, ModelAndView mav) {
        if (error != null) {
            mav.addObject("error", "Invalid username or password");
        }
        mav.setViewName("user/login.html");
        return mav;
    }

}
