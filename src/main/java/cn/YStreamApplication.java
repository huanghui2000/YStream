package cn;

import cn.scanner.ScannerApplication;
import cn.trigger.PolicyAssembler;
import cn.trigger.frame.Tactics;

import java.util.ArrayList;

/**
 * 流处理系统的启动类
 * 启动扫描器和获取触发器汇总的策略集合
 */
public class YStreamApplication {
    static ArrayList<Tactics> tacticList = new ArrayList<>();

    /**
     * 启动扫描器
     * 装填触发器汇总的策略集合
     */
    public static void run(Class<?> clazz) {
        // 启动扫描器
        ScannerApplication.run(clazz);
        // 装填触发器汇总的策略集合
        tacticList = PolicyAssembler.getTacticList();
    }

    public static ArrayList<Tactics> getTacticList() {
        return tacticList;
    }
}
