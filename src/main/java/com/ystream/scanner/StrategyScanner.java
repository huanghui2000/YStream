package com.ystream.scanner;

import com.ystream.scanner.frame.Scanner;
import com.ystream.scanner.util.ScannerTool;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * 策略扫描器
 * 用于扫描策略,
 * 返回的数据类型为ArrayList<Tactics>
 */
@Slf4j
public class StrategyScanner implements Scanner {
    private static final ArrayList<Class<?>> tactics = new ArrayList<>();

    /**
     * 扫描scanner包同级包trigger下的子包strategy的所有策略
     */
    @Override
    public void scanAll() {
        String packageName = StrategyScanner.class.getPackage().getName().replace("scanner", "trigger.strategy");
        //收集策略类
        ScannerTool.getPackClass(packageName, tactics);
    }

    @Override
    public ArrayList<Class<?>> getScanResult() {
        scanAll();
        log.info("策略扫描器加载完毕");
        return tactics;
    }
}
