package com.ystream.trigger;

import com.ystream.scanner.ScannerApplication;
import com.ystream.trigger.frame.Tactics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 数据中转和分类器
 * 1.接收来自扫描器的策略集和标记集
 * 2.处理策略集和标记集将其转化为需要的数据结构
 */
@SuppressWarnings({"unchecked"})
public class DataClassifier {
    public static void run(Class<?> clazz) {
        //启动ScannerApplication
        ScannerApplication.run(clazz);
    }

    /**
     * 中传从ScannerApplication获取策略集,
     * 根据@分类为HashMap<Class<? extends Annotation>, ArrayList<Class<?>>>
     */
    public static HashMap<Class<? extends Annotation>, ArrayList<Class<?>>> getPolicyCollection() {
        //获取所有的策略类，将Class<?>转换为Class<Tactics>
        ArrayList<Class<Tactics>> classes = convertClassList(ScannerApplication.getPolicyCollection());
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
     * 根据@分类为ashMap<Class<? extends Annotation>, HashMap<HashMap<String, String>,ArrayList<Method>>>
     */
    public static HashMap<Class<? extends Annotation>, HashMap<HashMap<String, String>, ArrayList<Method>>> getMarkupContainers() {
        //获取所有的标记类
        HashMap<Annotation, ArrayList<Method>> markupContainers = ScannerApplication.getMarkupContainers();
        //将注解类型分类
        HashMap<Class<? extends Annotation>, HashMap<HashMap<String, String>, ArrayList<Method>>> markupCollection = new HashMap<>();
        for (Annotation annotation : markupContainers.keySet()) {
            //获取注解的类型
            Class<? extends Annotation> annotationType = annotation.annotationType();
            //获取注解的方法
            ArrayList<Method> methods = markupContainers.get(annotation);
            for (Method method : methods) {
                //获取注解的属性
                HashMap<String, String> annoString = getAnnoString(annotationType, method);
                //判断标记集中是否存在该注解类型
                if (markupCollection.containsKey(annotationType)) {
                    //判断是否存在对应的属性HashMap，如果有将方法添加进方法列表中
                    if (markupCollection.get(annotationType).containsKey(annoString)) {
                        markupCollection.get(annotationType).get(annoString).add(method);
                    } else {
                        //如果不存在，创建一个新的方法列表
                        ArrayList<Method> list = new ArrayList<>();
                        list.add(method);
                        //将方法列表添加到对应的HashMap中
                        markupCollection.get(annotationType).put(annoString, list);
                    }
                } else {
                    //如果不存在，创建一个添加一个新的注解类型的HashMap
                    markupCollection.put(annotationType, new HashMap<>());
                    //将方法和属性添加到对应的HashMap中
                    HashMap<HashMap<String, String>, ArrayList<Method>> map = new HashMap<>();
                    ArrayList<Method> list = new ArrayList<>();
                    list.add(method);
                    map.put(annoString, list);
                    markupCollection.get(annotationType).putAll(map);
                }

            }
        }
        return markupCollection;
    }

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

    /**
     * 获取方法中所有参数的值
     * 添加为HashMap<String,String>
     */
    public static HashMap<String, String> getAnnoString(Class<? extends Annotation> anno, Method method) {
        HashMap<String, String> map = new HashMap<>();
        //获取注解的实例化
        Annotation annotation = method.getAnnotation(anno);
        //拼接注解的所有属性，利用/分割
        Method[] methods1 = anno.getDeclaredMethods();
        for (Method m : methods1) {
            try {
                Object value = m.invoke(annotation);
                map.put(m.getName(), value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
