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
public class ExcludeType extends Tactics {   //所属@StreamOperation
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
     * 本策略集是排除策略，需要不存在
     */
    @Override
    public boolean isTactics(HashMap<String, String> map, Method method) {
        //获取属性type，查看是EXCLUDE
        if (map.get("type").equals("EXCLUDE")) {
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
        //遍历triggerArgs，如果包含message，返回false
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (!message.contains(args.get(0))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> process(String message) {
        //遍历triggerArgs，如果不包含message，执行对应的方法，将结果添加到result中
        ArrayList<String> result = new ArrayList<>();
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (!message.contains(args.get(0))) {
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
