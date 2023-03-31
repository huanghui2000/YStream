package com.ystream.scanner;

import com.ystream.scanner.frame.Scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 类方法扫描器
 * 用于扫描类列表中的
 * 所有注解了来自函数注解包的注解的方法
 */
public class MethodScanner implements Scanner {
    private final ArrayList<Method> scanResult = new ArrayList<>();
    //需要扫描的类
    private final ArrayList<Class<?>> clazzScanResult;

    MethodScanner(ArrayList<Class<?>> clazzScanResult) {
        this.clazzScanResult = clazzScanResult;
    }

    /**
     * 函数扫描器的扫描方法
     * 获取类扫描器的扫描结果进行扫描
     * 对包含函数注解的函数进行标记
     */
    @Override
    public void scanAll() {
        //获取函数注解包扫描器的扫描结果
        ArrayList<Class<? extends Annotation>> annoScanResult = new MethodAnnoScanner().getScanResult();
        //遍历函数注解包扫描器的扫描结果
        for (Class<? extends Annotation> anno : annoScanResult) {
            //遍历类扫描器的扫描结果
            for (Class<?> clazz : clazzScanResult) {
                //获取类中的所有方法
                Method[] methods = clazz.getDeclaredMethods();
                //判断方法是否包含函数注解
                for (Method method : methods)
                    if (method.isAnnotationPresent(anno))
                        scanResult.add(method);
            }
        }
    }

    /**
     * 函数扫描器的扫描结果
     *
     * @return ArrayList<MarkupContainer>
     */
    @Override
    public ArrayList<Method> getScanResult() {
        scanAll();
        return scanResult;
    }

}
