package com.stoyanov.onlineshoestore.app.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    private static final String FAVICON =
            "https://res.cloudinary.com/dxvn93xbh/image/upload/v1580304755/important/favicon.png";

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        if (modelAndView != null) {
            modelAndView.addObject("favicon", FAVICON);
        }
    }
}