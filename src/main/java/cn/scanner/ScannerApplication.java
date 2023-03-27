package cn.scanner;


import cn.scanner.beans.MarkupContainer;

import java.util.ArrayList;

/**
 * 扫描器的启动类
 * 1.应该包含启动的类，所有的扫描器只需要扫描这个类的包和其子包下的所有类即可
 * 2.应该有操作所有的扫描器，并且将所有的扫描结果返回
 * 3.一切的变量都是static的，可以让所有的扫描器共享
 */
@SuppressWarnings("unused")
public class ScannerApplication {
    private static Class<?> mainClassAddress;
    private static ArrayList<MarkupContainer> markupContainers = new ArrayList<>();

    /**
     * 启动扫描器,配置启动类的地址
     */
    public static void run(Class<?> mainClassAddress) {
        //设置启动类的地址
        ScannerApplication.mainClassAddress = mainClassAddress;
        markupContainers = new MethodScanner().getScanResult();
    }

    /**
     * 获取启动类的地址
     *
     * @return String
     */
    public static Class<?> getMainClassAddress() {
        return mainClassAddress;
    }

    /**
     * 获取所有的扫描结果
     *
     * @return ArrayList<MarkupContainer>
     */
    public static ArrayList<MarkupContainer> getMarkupContainers() {
        return markupContainers;
    }
}
