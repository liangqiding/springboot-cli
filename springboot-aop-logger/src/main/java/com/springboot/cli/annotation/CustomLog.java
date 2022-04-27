package com.springboot.cli.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author : ding
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface CustomLog {

    /**
     * value 描述
     */
    String value() default "";

}
