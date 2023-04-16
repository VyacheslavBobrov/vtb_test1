package ru.bobrov.vyacheslav.test2.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Times {
    int count() default 1;
}
