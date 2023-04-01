package com.ystream;

import com.ystream.trigger.frame.Tactics;

import java.util.ArrayList;

/**
 * 通道类，用于处理数据
 */
public class YChannel {
    //返回的数据
    ArrayList<String> result = new ArrayList<>();
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
        ArrayList<String> resultList = new ArrayList<>();
        //遍历策略集
        for (Tactics tactics : tacticList) {
            //将策略集实例化
            if (tactics.isTrigger(msg)) {
                //判断tactics.process(msg)是否为空
                ArrayList<String> temp = tactics.process(msg);
                if (temp != null)
                    //将两个集合合并
                    resultList.addAll(temp);
            }
        }
        result = resultList;
        if (result.size() == 0)
            result.add("该信息不触发任何策略集");
    }

    /**
     * 获取处理结果
     */
    public ArrayList<String> getResult() {
        return result;
    }
}
