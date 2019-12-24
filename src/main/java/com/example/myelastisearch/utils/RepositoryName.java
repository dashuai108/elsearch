package com.example.myelastisearch.utils;


import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryName {

    public String value() default "";
}
