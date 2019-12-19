package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlerController {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ModelAndView notFound(Exception exception) {
//        ModelAndView mav = new ModelAndView("error/error.html");
//        mav.addObject("error", exception.getMessage());
//        return mav;
//    }

    //@ExceptionHandler(Exception.class)
//    public String handleException(Exception exception) {
//        return "error.html";
//    }
}
