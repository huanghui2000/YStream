package cn.trigger;

import cn.Main;
import cn.scanner.ScannerApplication;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 装配策略集和标记集
 */
public class PolicyCollection {

    public static void main(String[] args) {
        ScannerApplication.run(Main.class);
        new PolicyCollection().run();
    }

    //策略集和标记集的映射
    public void run() {
        //获取策略集和标记集
        HashMap<Class<? extends Annotation>, ArrayList<Class<?>>> policyCollection = TriggerApplication.getPolicyCollection();
        HashMap<Annotation, Method> markupContainers = TriggerApplication.getMarkupContainers();
        System.out.println("=========策略集=========");
        for (Class<? extends Annotation> annotation : policyCollection.keySet()) {
            System.out.println(annotation.getName());
            for (Class<?> aClass : policyCollection.get(annotation)) {
                System.out.println("    " + aClass.getName());
            }
        }
        System.out.println("=========标记集=========");
        for (Annotation annotation : markupContainers.keySet()) {
            System.out.println(annotation);
            System.out.println("    " + markupContainers.get(annotation));
        }
    }

}
