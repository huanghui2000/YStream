package com.ystream;

import com.ystream.trigger.frame.Tactics;
import com.ystream.trigger.PolicyAssembler;

import java.util.ArrayList;

/**
 * 流处理系统的启动类
 * 启动扫描器和获取触发器汇总的策略集合
 */
public class YStreamApplication {
    /**
     * 最终装配结束的遍历策略集
     */
    static ArrayList<Tactics> tacticList = new ArrayList<>();
    /**
     * 其中的中心类地址
     */
    public static Class<?> clazzAddress;

    /**
     * 启动扫描器
     * 装填触发器汇总的策略集合
     */
    public static void run(Class<?> clazz) {
        // 装配类触发地址
        clazzAddress = clazz;
        // 装填触发器汇总的策略集合
        tacticList = PolicyAssembler.getTacticList();
    }

    public static ArrayList<Tactics> getTacticList() {
        return tacticList;
    }
}
