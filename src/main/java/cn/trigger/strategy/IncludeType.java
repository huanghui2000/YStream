package cn.trigger.strategy;

import cn.anno.method.StreamOperation;
import cn.trigger.frame.Tactics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 包含策略 INCLUDE
 */
public class IncludeType extends Tactics {
    //所属@StreamOperation
    private Class<? extends Annotation> annoType = StreamOperation.class;
    //编辑集合
    private HashMap<ArrayList<String>, Method> triggerArgs = new HashMap<>();

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
            triggerArgs.put(args, null);
            return true;
        }
        return false;
    }

    @Override
    public boolean isTrigger(String message) {
        //遍历triggerArgs，如果包含message，返回true
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (args.contains(message))
                return true;
        }
        return false;
    }

    @Override
    public String process(String message) {
        //遍历triggerArgs，如果包含message，执行对应的方法
        for (ArrayList<String> args : triggerArgs.keySet()) {
            if (args.contains(message)) {
                try {
                    //判断是否有返回值，如果没有则返回null
                    if (triggerArgs.get(args).getReturnType().equals(void.class))
                        return null;
                    //如果有返回值，返回返回值
                    return (String) triggerArgs.get(args).invoke(this, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
