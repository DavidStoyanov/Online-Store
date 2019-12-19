package com.stoyanov.onlineshoestore.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableSpringConfigured
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()

                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/**/favicon.ico", "/offers", "/offer/details/**", "/contacts").permitAll()
                .antMatchers("/", "/index", "/user/register", "/user/login").anonymous()
                .antMatchers("/offer/create/**", "offer/edit/**").hasAnyAuthority("MODERATOR", "ROOT")
                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)

                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login?logout").permitAll()

                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized");
    }
}
