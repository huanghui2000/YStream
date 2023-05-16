package com.ystream.scanner;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@SuppressWarnings("unused")
public class ScannerApplication {
    //启动类的地址
    private static Class<?> mainClassAddress;
    //所有的扫描结果
    private static HashMap<Annotation, ArrayList<Method>> markupContainers = new HashMap<>();
    private static ArrayList<Class<?>> policyCollection = new ArrayList<>();

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
        log.info("正在启动扫描器");
        //设置启动类的地址
        ScannerApplication.mainClassAddress = mainClassAddress;
        //汇总所有的扫描结果
        scanSummary();
    }

    /**
     * 汇总扫描器的扫描结果
     * 1.获取类扫描器的扫描结果
     * 2.获取函数扫描器的扫描结果
     * 3.封装为HashMap<Annotation, Method>
     * 4.获取策略集扫描器的扫描结果
     */
    public static void scanSummary() {
        //获取类扫描器的扫描结果
        ArrayList<Class<?>> classList = new ClazzScanner(mainClassAddress.getPackage().getName()).getScanResult();
        //获取函数注解包扫描器的扫描结果
        ArrayList<Method> methodList = new MethodScanner(classList).getScanResult();
        //新建一个MarkupContainer列表
        HashMap<Annotation, ArrayList<Method>> markupContainerList = new HashMap<>();
        //遍历函数扫描器的扫描结果
        for (Method method : methodList) {
            //获取函数的注解
            Annotation[] annotations = method.getAnnotations();
            //将注解和对应的函数写入HashMap HashMap<Annotation, ArrayList<Method>>
            for (Annotation annotation : annotations) {
                //判断是否存在注解，如果有则添加到Method列表中
                if (markupContainerList.containsKey(annotation)) {
                    markupContainerList.get(annotation).add(method);
                } else {
                    //如果没有则新建一个Method列表
                    ArrayList<Method> methodArrayList = new ArrayList<>();
                    methodArrayList.add(method);
                    markupContainerList.put(annotation, methodArrayList);
                }
            }
        }
        //将所有的扫描结果添加到静态变量中
        markupContainers = markupContainerList;
        policyCollection = new StrategyScanner().getScanResult();
    }

    /**
     * 获取类和函数所有的扫描结果
     *
     * @return ArrayList<MarkupContainer>
     */
    public static HashMap<Annotation, ArrayList<Method>> getMarkupContainers() {
        return markupContainers;
    }

    /**
     * 获取策略集所有的扫描结果
     *
     * @return ArrayList<Class < ?>>
     */
    public static ArrayList<Class<?>> getPolicyCollection() {
        return policyCollection;
    }
}
