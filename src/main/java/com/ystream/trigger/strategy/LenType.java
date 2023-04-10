package com.ystream.trigger.strategy;

import com.ystream.anno.method.StreamLen;
import com.ystream.trigger.frame.Tactics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 流长度策略 LEN
 */
@SuppressWarnings("unused")
public class LenType extends Tactics {
    //所属@StreamLen
    private final Class<? extends Annotation> annoType = StreamLen.class;

    //包含表达式的触发集合，表示某个长度的流触发了哪些函数
    private static final HashMap<Integer, ArrayList<Method>> triggerArgs = new HashMap<>();

    @Override
    public HashMap<?, ArrayList<Method>> getTriggerArgs() {
        return triggerArgs;
    }

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return annoType;
    }

    /**
     * 某标注的属性集是否是触发本策略集的条件
     * 本策略集是 流长度策略，需要存在属性len
     */
    @Override
    public boolean isTactics(HashMap<String, String> map, ArrayList<Method> methods) {
        //获取属性len，查看是否存在
        if (map.containsKey("len")) {
            //如果存在，将属性len和方法集合加入触发集合
            int len = Integer.parseInt(map.get("len"));
            triggerArgs.put(len, methods);
            return true;
        }
        return false;
    }

    /**
     * 判断消息是否是触发本策略集
     */
    @Override
    public boolean isTrigger(String message) {
        //遍历triggerArgs，如果长度匹配message，返回true
        for (int len : triggerArgs.keySet()) {
            if (message.length() == len) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> process(String message) {
        //遍历triggerArgs，如果长度匹配message，执行对应方法集的方法
        ArrayList<String> result = new ArrayList<>();
        //遍历triggerArgs，查找匹配message
        for (int len : triggerArgs.keySet()) {
            if (message.length() == len) {
                try {
                    //如果匹配，执行对应方法集的方法
                    for (Method method : triggerArgs.get(len)) {
                        result.add(invokeTriggerFunction(method, message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.add("处理策略: " + triggerArgs.get(len) + " 的时候执行失败");
                }
            }
        }
        return result;
    }
}
