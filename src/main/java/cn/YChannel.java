package cn;

import cn.trigger.frame.Tactics;

import java.util.ArrayList;

/**
 * 通道类，用于处理数据
 */
public class YChannel {
    //返回的数据
    String result = "暂无数据";
    //从YStreamApplication启动器获取策略集
    ArrayList<Tactics> tacticList;

    public YChannel() {
        tacticList = YStreamApplication.getTacticList();
    }

    /**
     * 处理数据
     * 遍历策略集，对每个策略进行可用性检测，如果可用则执行策略
     */
    public void process(String msg) {
        //遍历策略集
        for (Tactics tactics : tacticList) {
            //将策略集实例化
            if (tactics.isTrigger(msg)) {
                //如果策略可用，则执行策略
                result = tactics.process(msg);
                return;
            }
        }
        result = "该数据不存在处理策略";
    }

    /**
     * 获取处理结果
     */
    public String getResult() {
        return result;
    }
}
