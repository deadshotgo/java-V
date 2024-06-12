package com.example.dom.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({})
public @interface GenerateApiDoc {
    String summary() default "";
    String description() default "";
    String responseCode() default "201";
    String responseDescription() default "";
    Class<?> responseClass();
    String mediaType() default "application/json";
}