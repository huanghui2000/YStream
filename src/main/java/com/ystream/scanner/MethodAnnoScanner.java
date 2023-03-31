package com.ystream.scanner;

import com.ystream.scanner.util.ScannerTool;
import com.ystream.scanner.frame.Scanner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * 用于获取方法注解包的所有注解列表
 */
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
        return ScannerTool.toAnnotationList(scanResult);
    }
}
