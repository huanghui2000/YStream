package cn.scanner;

import cn.scanner.beans.MarkupContainer;
import cn.scanner.frame.Scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import cn.scanner.util.ScannerTool;

/**
 * 类方法扫描器
 * 用于扫描类列表中的方法
 * 所有注解了来自函数注解包的注解的方法
 */
public class MethodScanner implements Scanner {
    private final ArrayList<MarkupContainer> markupContainers = new ArrayList<>();

    /**
     * 函数扫描器的扫描方法
     * 获取类扫描器的扫描结果进行扫描
     * 对包含函数注解的函数进行标记，封装进MarkupContainer的List中
     */
    @Override
    public void scanAll() {
        //获取类扫描器的扫描结果
        ArrayList<Class<?>> clazzScanResult = new ClazzScanner().getScanResult();
        //获取函数注解包扫描器的扫描结果
        ArrayList<Class<? extends Annotation>> annoScanResult = new MethodAnnoScanner().getScanResult();
        //遍历函数注解包扫描器的扫描结果
        for (Class<? extends Annotation> anno : annoScanResult) {
            MarkupContainer markupContainer = new MarkupContainer(anno, new HashMap<>());
            //遍历类扫描器的扫描结果
            for (Class<?> clazz : clazzScanResult) {
                //获取类中的所有方法
                Method[] methods = clazz.getDeclaredMethods();
                //判断方法是否包含函数注解
                for (Method method : methods)
                    if (method.isAnnotationPresent(anno)) {
                        //获取函数注解的所有的属性值
                        String annotationStr = ScannerTool.getAnnoString(anno, method);
                        //封装进MarkupContainer对应的Map中
                        markupContainer.getContent().put(annotationStr, method);
                    }
            }
            markupContainers.add(markupContainer);
        }
    }

    /**
     * 函数扫描器的扫描结果
     *
     * @return ArrayList<MarkupContainer>
     */
    @Override
    public ArrayList<MarkupContainer> getScanResult() {
        scanAll();
        return markupContainers;
    }

}
