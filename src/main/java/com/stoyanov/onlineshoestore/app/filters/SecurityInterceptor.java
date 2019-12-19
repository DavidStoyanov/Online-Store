package com.stoyanov.onlineshoestore.app.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class SecurityInterceptor implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long count = userDetails.getAuthorities().stream()
                .filter(x -> x.getAuthority().equals("ROLE_ANONYMOUS"))
                .filter(x -> x.getAuthority().equals("USER"))
                .filter(x -> x.getAuthority().equals("ROOT"))
                .count();

        boolean offerRoute= false;
        if (req.getRequestURI().startsWith("/offer/create/")) {
            offerRoute = true;
        } else if (req.getRequestURI().startsWith("/offer/edit/")) {
            offerRoute = true;
        }

        if (count == 0 && offerRoute) {
            resp.sendRedirect("/home");
            return;
        }

        filterChain.doFilter(req, resp);
    }
}
