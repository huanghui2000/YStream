package com.ystream.anno.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法注解
 * 用于标记方法
 * 包含一个枚举类型的参数，选择操作类型
 * 一个string类型的参数，写入操作内容
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface StreamOperation {
    //操作类型
    OperationType type();

    //操作内容
    String content();
}
