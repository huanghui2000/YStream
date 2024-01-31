package com.ystream.scanner;

import com.ystream.scanner.util.ScannerTool;
import com.ystream.scanner.frame.Scanner;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * 用于获取方法注解包的所有注解列表
 */
@Slf4j
public class MethodAnnoScanner implements Scanner {

    private final ArrayList<Class<?>> scanResult = new ArrayList<>();

    /**
     * 扫描scanner包同级包anno下的子包method的所有注解
     */
    @Override
    public void scanAll() {
        String packageName = MethodAnnoScanner.class.getPackage().getName().replace("scanner", "anno.method");
        ScannerTool.getPackClass(packageName, scanResult);
    }

    /**
     * 返回注解包的所有注解列表
     */
    @Override
    public ArrayList<Class<? extends Annotation>> getScanResult() {
        scanAll();
        log.info("- 函数注解扫描器加载完毕        共获取 {} 个策略注解    -", scanResult.size());
        return ScannerTool.toAnnotationList(scanResult);
    }
}
