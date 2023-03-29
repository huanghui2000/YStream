package cn.trigger.frame;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 触发器策略集的抽象类
 * 包含了
 * 1.会触发的注解
 * 2.某标注的属性集是否是触发本策略集的条件,
 * 如果是则加入触发函数的参数集
 * 3.流是否会触发本策略集
 * 3.如果触发了，会触发哪个函数
 * 4.标记集合<触发函数的参数集,触发函数>
 */
@SuppressWarnings("unused")
public abstract class Tactics {
    //所属的注解类型
    private Class<? extends Annotation> annoType;
    //触发函数的参数集,其中key可以为多种类型
    private HashMap<?, Method> triggerArgs;

    /**
     * 获取注解
     */
    abstract public Class<? extends Annotation> getAnnotationType();

    /**
     * 某标注的属性集String是否是触发本策略集的条件
     */
    abstract public boolean isTactics(HashMap<String, String> map, Method method);

    /**
     * 判断处理流是否触发了策略集
     */
    abstract public boolean isTrigger(String message);

    /**
     * 处理流触发某个函数
     */
    abstract public String process(String message);

}
