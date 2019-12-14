package com.stoyanov.onlineshoestore.app.controllers.base;

import com.stoyanov.onlineshoestore.app.models.service.user.UserSessionModel;

import javax.servlet.http.HttpSession;

public class BaseController {

    private static final String USER_SESSION_MODEL_NAME = "user";

    protected String getUsername(HttpSession httpSession) {
        return ((UserSessionModel) httpSession.getAttribute(USER_SESSION_MODEL_NAME)).getUsername();
    }

    protected void renewAuthentication(HttpSession httpSession, UserSessionModel userServiceModel) {
        httpSession.setAttribute(USER_SESSION_MODEL_NAME, userServiceModel);
    }

    protected void invalidateAuthentication(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
