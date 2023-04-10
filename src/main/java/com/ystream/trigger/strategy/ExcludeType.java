package com.ystream.trigger.strategy;

import com.ystream.trigger.frame.Tactics;
import com.ystream.anno.method.StreamOperation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 排除策略 EXCLUDE
 */
@SuppressWarnings("unused")
public class ExcludeType extends Tactics {
    //所属@StreamOperation
    private final Class<? extends Annotation> annoType = StreamOperation.class;

    //获取注解类型
    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return annoType;
    }

    //包含表达式的触发集合，表示某个消息的关键字触发了哪些函数
    private static final HashMap<ArrayList<String>, ArrayList<Method>> triggerArgs = new HashMap<>();

    @Override
    public HashMap<ArrayList<String>, ArrayList<Method>> getTriggerArgs() {
        return triggerArgs;
    }

    /**
     * 某标注的属性集是否是触发本策略集的条件
     * 本策略集是 包含策略，需要属性type为EXCLUDE
     */
    @Override
    public boolean isTactics(HashMap<String, String> map, ArrayList<Method> methods) {
        //获取属性type，查看是REGEXP
        if (map.get("type").contains("EXCLUDE")) {
            //如果是REGEXP，将属性content和方法集合加入触发集合
            ArrayList<String> args = new ArrayList<>();
            args.add(map.get("content"));
            triggerArgs.put(args, methods);
            return true;
        }
        return false;
    }

    /**
     * 判断消息是否是触发本策略集
     */
    @Override
    public boolean isTrigger(String message) {
        //遍历triggerArgs，如果匹配message，返回true
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (!message.contains(args.get(0))) {
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
    public ArrayList<String> process(String message) {
        //遍历triggerArgs，如果匹配message，执行对应的方法，将结果添加到result中
        ArrayList<String> result = new ArrayList<>();
        //遍历triggerArgs，查找匹配message
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (!message.contains(args.get(0))) {
                try {
                    //如果匹配，执行对应方法集的方法
                    for (Method method : triggerArgs.get(args)) {
                        result.add(invokeTriggerFunction(method, message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.add("处理策略: " + triggerArgs.get(args) + " 的时候执行失败");
                }
            }
        }
        return result;
    }


}