package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    String value() default "";

    RequestMethod[] method() default {};
}

