package com.springapp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeAPI {
    String[] arguments() default {};
}
