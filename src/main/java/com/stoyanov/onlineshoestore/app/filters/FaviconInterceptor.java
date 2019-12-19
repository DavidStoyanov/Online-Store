package com.stoyanov.onlineshoestore.app.filters;

import com.stoyanov.onlineshoestore.app.annotations.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    private static final String FAVICON = "https://res.cloudinary.com/dxvn93xbh/image/upload/favicon.ico";

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println();
        if (modelAndView != null) {
            modelAndView.addObject("favicon", FAVICON);
        }
    }
}