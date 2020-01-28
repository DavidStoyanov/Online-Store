package com.stoyanov.onlineshoestore.app.web.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        exception.printStackTrace();
        return "errors/global-error.html";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception) {
        exception.printStackTrace();
        return "error.html";
    }

}
