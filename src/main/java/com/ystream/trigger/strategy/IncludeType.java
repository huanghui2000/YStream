package com.ystream.trigger.strategy;

import com.ystream.anno.method.StreamOperation;
import com.ystream.trigger.frame.Tactics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 包含策略 INCLUDE
 */

public class IncludeType extends Tactics {
    //所属@StreamOperation
    private final Class<? extends Annotation> annoType = StreamOperation.class;
    //编辑集合
    private static final HashMap<ArrayList<String>, Method> triggerArgs = new HashMap<>();

    public HashMap<ArrayList<String>, Method> getTriggerArgs() {
        return triggerArgs;
    }

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return annoType;
    }

    /**
     * 某标注的属性集是否是触发本策略集的条件
     * 本策略集是包含策略，需要存在
     */
    @Override
    public boolean isTactics(HashMap<String, String> map, Method method) {
        //获取属性type，查看是INCLUDE
        if (map.get("type").equals("INCLUDE")) {
            ArrayList<String> args = new ArrayList<>();
            args.add(map.get("content"));
            triggerArgs.put(args, method);
            return true;
        }
        return false;
    }

    /**
     * 判断消息是否是触发本策略集
     */
    @Override
    public boolean isTrigger(String message) {
        //遍历triggerArgs，如果包含message，返回true
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (message.contains(args.get(0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 执行本策略集
     * 对触发的消息的方法进行调用
     */
    @Override
    public String process(String message) {
        //遍历triggerArgs，如果包含message，执行对应的方法
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (message.contains(args.get(0))) {
                try {
                    return invokeTriggerFunction(triggerArgs.get(args), message);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "处理策略时出现异常";
                }
            }
        }
        return "该数据不存在处理策略";
    }

    /**
     * 触发函数
     */
    public static String invokeTriggerFunction(Method method, Object arg) throws Exception {
        // 判断函数是否为静态函数
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        // 如果不是静态函数，则创建一个对象实例
        Object obj = isStatic ? null : method.getDeclaringClass().getDeclaringClass();

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
