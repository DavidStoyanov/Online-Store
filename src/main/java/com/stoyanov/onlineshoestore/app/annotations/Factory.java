package com.stoyanov.onlineshoestore.app.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Factory {
    @AliasFor(annotation = Component.class)
    String value() default "";
}