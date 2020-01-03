package com.stoyanov.onlineshoestore.app.config;

import com.stoyanov.onlineshoestore.app.filters.FaviconInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    private final FaviconInterceptor faviconInterceptor;

    @Autowired
    public ApplicationWebConfiguration(FaviconInterceptor faviconInterceptor) {
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.faviconInterceptor);
    }
}
