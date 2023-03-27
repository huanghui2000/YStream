package cn.scanner;

import cn.scanner.frame.Scanner;
import cn.scanner.util.ScannerTool;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * 用于过去类注解包的所有注解列表
 */
public class ClazzAnnoScanner implements Scanner {

    private final ArrayList<Class<?>> scanResult = new ArrayList<>();

    /**
     * 扫描scanner包同级包anno的子包clazz的所有注解
     */
    @Override
    public void scanAll() {
        String packageName = ClazzAnnoScanner.class.getPackage().getName().replace("scanner", "anno.clazz");
        ScannerTool.getAnno(packageName, scanResult);
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
