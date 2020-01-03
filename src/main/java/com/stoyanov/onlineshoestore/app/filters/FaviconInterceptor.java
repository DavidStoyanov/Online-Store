package com.stoyanov.onlineshoestore.app.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    private static final String FAVICON = "https://p-sf1.pcloud.com/DLZipFp0SZetovCSZEz9oZZV78cN7Z3VZZPoFZZvA4ZNVZ25Z0VZ1gYeSH2pWiHR7sKihcpXIRMjeQiX";

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