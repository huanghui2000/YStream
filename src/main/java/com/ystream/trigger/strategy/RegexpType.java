package com.ystream.trigger.strategy;

import com.ystream.anno.method.StreamOperation;
import com.ystream.trigger.frame.Tactics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 正则策略  REGEXP
 */
public class RegexpType extends Tactics {
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
     * 本策略集是 正则策略，需要匹配正则表达式
     */
    @Override
    public boolean isTactics(HashMap<String, String> map, Method method) {
        //获取属性type，查看是INCLUDE
        if (map.get("type").equals("REGEXP")) {
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
        //遍历triggerArgs，如果匹配message，返回true
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (message.matches(args.get(0))) {
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
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (message.matches(args.get(0))) {
                try {
                    result.add(invokeTriggerFunction(triggerArgs.get(args), message));
                } catch (Exception e) {
                    e.printStackTrace();
                    result.add("处理策略: " + triggerArgs.get(args) + " 的时候执行失败");
                }
            }
        }
        return result;
    }


}