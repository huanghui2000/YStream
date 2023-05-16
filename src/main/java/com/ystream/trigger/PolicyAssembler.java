package com.ystream.trigger;

import com.ystream.YStreamApplication;
import com.ystream.trigger.frame.Tactics;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 将标记集装配到对应的策略集中
 * 返回装配后的策略集
 */
@Slf4j
@SuppressWarnings({"unused"})
public class PolicyAssembler {
    static ArrayList<Tactics> tacticList = new ArrayList<>();

    /**
     * 封装所有被标记的策略集
     */
    public static ArrayList<Tactics> getTacticList() {
        //启动数据分类器获取所有标记集
        DataClassifier.run(YStreamApplication.clazzAddress);
        // 启动数据整合器获取所有数据集进行装配
        strategyAssembly();
        log.info("策略集合装载成功");
        return tacticList;
    }

    /**
     * 将标记集装配到对应的策略集中
     */
    public static void strategyAssembly() {
        //获取策略集和标记集
        HashMap<Class<? extends Annotation>, ArrayList<Class<?>>> policyCollection = DataClassifier.getPolicyCollection();
        HashMap<Class<? extends Annotation>, HashMap<HashMap<String, String>, ArrayList<Method>>> markupContainers = DataClassifier.getMarkupContainers();
        //根据标记类型分类遍历标记集
        for (Class<? extends Annotation> annoType : markupContainers.keySet()) {
            //获取标记集中的标记
            HashMap<HashMap<String, String>, ArrayList<Method>> markupContainer = markupContainers.get(annoType);
            //获取策略集对应标记类型的策略集
            ArrayList<Class<?>> policyList = policyCollection.get(annoType);
            //遍历策略集
            for (Class<?> policy : policyList) {
                //实例化策略
                Tactics tactic = null;
                try {
                    tactic = (Tactics) policy.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                //遍历标记集
                for (HashMap<String, String> markup : markupContainer.keySet()) {
                    //获取标记集中的标记
                    ArrayList<Method> methods = markupContainer.get(markup);
                    //对策略进行装配
                    if (tactic != null) {
                        tactic.isTactics(markup, methods);
                    }
                }
                tacticList.add(tactic);
            }
        }
    }
}

