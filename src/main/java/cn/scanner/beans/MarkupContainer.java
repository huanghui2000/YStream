package cn.scanner.beans;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解数据类
 * 用于存储注解数据
 * 数据结构如下
 * Class<？ extends Annotation>：注解
 * Map：<注解操作内容，触发的方法>
 * 其中操作类型为枚举类型
 * 操作内容为注解的属性
 * 触发的方法为注解的方法
 */
@SuppressWarnings("unused")
public class MarkupContainer {
    //注解
    private Class<? extends Annotation> annotation;
    //注解中若干个属性的值:获取的方法
    private HashMap<String, Method> content;

    public MarkupContainer(Class<? extends Annotation> annotation, HashMap<String, Method> content) {
        this.annotation = annotation;
        this.content = content;
    }


    @Override
    public String toString() {
        return "注解为：" + annotation.getName() + "，操作内容：" + content;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public Map<String, Method> getContent() {
        return content;
    }

    public void setContent(HashMap<String, Method> content) {
        this.content = content;
    }
}
