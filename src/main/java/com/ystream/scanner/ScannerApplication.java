package com.ystream.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 扫描器的启动类
 * 1.应该包含启动的类，所有的扫描器只需要扫描这个类的包和其子包下的所有类即可
 * 2.应该有操作所有的扫描器，并且将所有的扫描结果返回
 * 3.一切的变量都是static的，可以让所有的扫描器共享
 */
@SuppressWarnings("unused")
public class ScannerApplication {
    //启动类的地址
    private static Class<?> mainClassAddress;
    //所有的扫描结果
    private static HashMap<Annotation, Method> markupContainers = new HashMap<>();

    /**
     * 获取启动类的地址
     *
     * @return String
     */
    public static Class<?> getMainClassAddress() {
        return mainClassAddress;
    }

    /**
     * 启动扫描器,配置启动类的地址,汇总所有的扫描结果
     */
    public static void run(Class<?> mainClassAddress) {
        //设置启动类的地址
        ScannerApplication.mainClassAddress = mainClassAddress;
        //汇总所有的扫描结果
        scanSummary();
    }

    /**
     * 汇总所有的扫描结果
     * 1.获取类扫描器的扫描结果
     * 2.获取函数扫描器的扫描结果
     * 3.封装为HashMap<Annotation, Method>
     */
    public static void scanSummary() {
        //获取类扫描器的扫描结果
        ArrayList<Class<?>> classList = new ClazzScanner(mainClassAddress.getPackage().getName()).getScanResult();
        //获取函数注解包扫描器的扫描结果
        ArrayList<Method> methodList = new MethodScanner(classList).getScanResult();
        //新建一个MarkupContainer列表
        HashMap<Annotation, Method> markupContainerList = new HashMap<>();
        //遍历函数扫描器的扫描结果
        for (Method method : methodList) {
            //获取函数的注解
            Annotation[] annotations = method.getAnnotations();
            //将注解和对应的函数写入HashMap<Annotation, Method>
            for (Annotation annotation : annotations)
                markupContainerList.put(annotation, method);
        }
        //将所有的扫描结果添加到静态变量中
        markupContainers = markupContainerList;
    }

    /**
     * 获取所有的扫描结果
     *
     * @return ArrayList<MarkupContainer>
     */
    public static HashMap<Annotation, Method> getMarkupContainers() {
        return markupContainers;
    }

    /**
     * 获取所有的策略
     *
     * @return ArrayList<Class < ?>>
     */
    public static ArrayList<Class<?>> getPolicyCollection() {
        return new StrategyScanner().getScanResult();
    }
}
