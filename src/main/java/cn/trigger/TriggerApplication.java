package cn.trigger;

import cn.scanner.ScannerApplication;
import cn.trigger.frame.Tactics;
import cn.trigger.util.TriggerTool;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 核心触发器
 */
public class TriggerApplication {


    /**
     * 中传从ScannerApplication获取策略集,
     * 根据@分类为HashMap<Class<? extends Annotation>, ArrayList<Class<?>>>
     */
    public static HashMap<Class<? extends Annotation>, ArrayList<Class<?>>> getPolicyCollection() {
        //获取所有的策略类，将Class<?>转换为Class<Tactics>
        ArrayList<Class<Tactics>> classes = TriggerTool.convertClassList(ScannerApplication.getPolicyCollection());
        //将策略类按照注解分类
        HashMap<Class<? extends Annotation>, ArrayList<Class<?>>> policyCollection = new HashMap<>();
        for (Class<Tactics> clazz : classes) {
            try {
                //实例化策略类
                Tactics annotation = clazz.newInstance();
                //判断策略类的注解是否存在
                Class<? extends Annotation> annotationType = annotation.getAnnotationType();
                //判断策略集中是否存在该注解类型
                if (policyCollection.containsKey(annotationType)) {
                    //如果存在，直接添加
                    policyCollection.get(annotationType).add(clazz);
                } else {
                    //如果不存在，创建一个新的ArrayList
                    ArrayList<Class<?>> list = new ArrayList<>();
                    list.add(clazz);
                    policyCollection.put(annotationType, list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return policyCollection;
    }

    /**
     * 中传从ScannerApplication获取标记集
     */
    public static HashMap<Annotation, Method> getMarkupContainers() {
        return ScannerApplication.getMarkupContainers();
    }
}
