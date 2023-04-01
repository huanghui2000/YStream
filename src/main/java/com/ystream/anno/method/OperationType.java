package com.ystream.anno.method;


/**
 * 枚举类型
 * 用于选择操作类型
 * 目前有
 * INCLUDE: 包含
 */
public enum OperationType {
    //包含类型，当流处理器中包含该字符串时，执行操作
    INCLUDE,
    //排除类型，当流处理器中不包含该字符串时，执行操作
    EXCLUDE,
    //正则表达式类型，当流处理器中符合该正则表达式时，执行操作
    REGEXP
}