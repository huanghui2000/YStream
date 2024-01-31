package com.ystream;

import com.ystream.trigger.frame.Tactics;
import com.ystream.trigger.PolicyAssembler;
import lombok.extern.slf4j.Slf4j;
import com.ystream.plug.BannerReader;

import java.util.ArrayList;

/**
 * 流处理系统的启动类
 * 启动扫描器和获取触发器汇总的策略集合
 */
@Slf4j
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
        log.info("YStream启动中");
        //绘制启动开头的Banner
        new BannerReader().printBanner();
        // 装配类触发地址
        clazzAddress = clazz;
        // 装填触发器汇总的策略集合
        tacticList = PolicyAssembler.getTacticList();
    }

    public static ArrayList<Tactics> getTacticList() {
        return tacticList;
    }
}
