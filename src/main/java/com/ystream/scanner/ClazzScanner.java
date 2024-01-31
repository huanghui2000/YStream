package com.ystream.scanner;

import com.ystream.scanner.frame.Scanner;
import com.ystream.scanner.util.ScannerTool;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 类扫描器
 * 用于扫描指定包下
 * 所有注解了来自类注解包的注解的类
 */
@Slf4j
public class ClazzScanner implements Scanner {
    private final ArrayList<Class<?>> scanResult = new ArrayList<>();
    //指定包名
    private final String packageName;

    ClazzScanner(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 类扫描器的扫描结果
     *
     * @return ArrayList<Class < ?>>
     */
    public ArrayList<Class<?>> getScanResult() {
        scanAll();
        log.info("- 类扫描器加载完毕             共获取 {} 个主动标记类  -", scanResult.size());
        return scanResult;
    }

    /**
     * 类扫描器的扫描方法
     * 基本原理是利用类加载器的特性，将所有的类加载到内存中
     * 其中中心启动类会记录其中的包名，
     * 扫描只需要扫描这个包和其子包下的所有类即可
     */
    public void scanAll() {
        try {
            //将包名转换为路径名
            String path = packageName.replace(".", "/");
            if (path.equals(""))
                throw new Exception("包名不能为空");
            //获取类加载器
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //获取指定包名下的所有资源
            Enumeration<URL> resources = classLoader.getResources(path);
            //遍历资源
            while (resources.hasMoreElements()) {
                //通过URL的getProtocol()方法判断是文件系统还是JAR文件
                URL url = resources.nextElement();
                if (url.getProtocol().equals("file"))
                    ScannerTool.findClassesInFileSystemPackage(packageName, url.getFile(), scanResult, new ClazzAnnoScanner().getScanResult());
                else if (url.getProtocol().equals("jar"))
                    ScannerTool.findClassesInJarPackage(packageName, url.getPath().substring(5, url.getPath().indexOf("!")), scanResult, new ClazzAnnoScanner().getScanResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
