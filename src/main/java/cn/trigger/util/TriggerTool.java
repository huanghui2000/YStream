package cn.trigger.util;

import cn.trigger.frame.Tactics;

import java.util.ArrayList;
import java.lang.annotation.Annotation;

/**
 * 触发器工具
 * 封装一些工具方法
 */
@SuppressWarnings("unchecked")
public class TriggerTool {
    /**
     * 将Class类型的类列表转换为类型Tactics的类列表
     */
    public static ArrayList<Class<Tactics>> convertClassList(ArrayList<Class<?>> classList) {
        ArrayList<Class<Tactics>> tacticsList = new ArrayList<>();
        for (Class<?> aClass : classList) {
            tacticsList.add((Class<Tactics>) aClass);
        }
        return tacticsList;
    }

}
