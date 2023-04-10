package com.ystream.anno.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流注解-长度注解
 * 有两种版本
 * 只有一个参数，表示流的长度
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface StreamLen {
    //流的长度
    int len() default 0;
}

