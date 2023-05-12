package com.ystream.trigger.frame;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 触发器策略集的抽象类
 * 包含了
 * 1.策略的源注解
 * 2.某标注的属性集是否是触发本策略集的条件,如果是则加入触发函数的参数集
 * 3.流是否会触发本策略集
 * 4.如果触发了，会触发哪个函数
 * 5.标记集合<触发的参数集,触发函数>
 * 6.触发函数
 */
@SuppressWarnings("unused")
public abstract class Tactics {
    //所属的注解类型
    private Class<? extends Annotation> annoType;
    //触发函数的参数集,其中函数可能会是多个
    private HashMap<?, ArrayList<Method>> triggerArgs;

    abstract public HashMap<?, ArrayList<Method>> getTriggerArgs();

    /**
     * 获取注解
     */
    abstract public Class<? extends Annotation> getAnnotationType();

    /**
     * 某标注的属性集HashMap<String, String>是否是触发本策略集的条件
     */
    abstract public void isTactics(HashMap<String, String> map, ArrayList<Method> methods);

    /**
     * 判断处理流是否触发了策略集
     */
    abstract public boolean isTrigger(String message);

    /**
     * 处理流触发相关函数
     */
    abstract public ArrayList<String> process(String message);

    /**
     * 触发函数
     */
    public static String invokeTriggerFunction(Method method, Object arg) throws Exception {
        // 判断函数是否为静态函数
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        // 如果不是静态函数，则创建一个对象实例
        Object obj = isStatic ? null : method.getDeclaringClass().newInstance();
        // 获取函数的参数类型列表和返回值类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();

        if (parameterTypes.length == 0) { // 无参函数
            if (returnType == void.class) {
                // 触发无返回值的函数
                method.invoke(obj);
                return "触发了函数但是没有返回值";
            } else {
                // 返回函数的返回值
                Object result = method.invoke(obj);
                return (result == null) ? "触发了函数但是没有返回值" : result.toString();
            }
        } else if (parameterTypes.length == 1 && parameterTypes[0] == String.class) { // 一个字符串参数的函数
            if (arg instanceof String) {
                // 触发函数，并返回结果
                Object result = method.invoke(obj, arg);
                return (result == null) ? "触发了函数但是没有返回值" : result.toString();
            } else {
                // 参数类型错误，则抛出异常
                throw new IllegalArgumentException("参数类型错误，需要提供 单个String 类型参数");
            }
        } else { // 参数数量大于 1
            throw new IllegalArgumentException("参数类型错误，需要提供 单个String 类型参数");
        }
    }


}
