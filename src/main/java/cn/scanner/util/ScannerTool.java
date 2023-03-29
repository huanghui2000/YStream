package cn.scanner.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描工具
 * 封装一些工具方法
 */
public class ScannerTool {
    /**
     * 在JAR文件中查找类
     * 利用JarFile遍历JAR文件中某个包下
     * 倘若注入了注解列表，则只返回注解列表中的注解了的类
     */
    public static void findClassesInJarPackage(String packageName, String jarFilePath, List<Class<?>> classes, ArrayList<Class<? extends Annotation>> anno) {
        JarFile jarFile = null;
        try {
            //利用JarFile读取jar包
            jarFile = new JarFile(jarFilePath);
            //获取jar包中的所有实体
            Enumeration<JarEntry> entry = jarFile.entries();
            //对jar包中的每个实体进行遍历
            while (entry.hasMoreElements()) {
                JarEntry jarEntry = entry.nextElement();
                String entryName = jarEntry.getName();
                //判断条件写入
                if (entryName.endsWith(".class") && !entryName.contains("springframework")) {
                    String className = entryName.substring(0, entryName.lastIndexOf(".")).replaceAll("/", ".");
                    className = className.substring(className.indexOf("BOOT-INF.classes.") + 17);
                    //判断是否是指定包名下的类
                    if (className.startsWith(packageName)) {
                        //如果注解列表不为空，则只返回注解列表中的注解了的类
                        isAnnotated(classes, anno, className);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //操作完成后关闭JarFile
            try {
                if (jarFile != null) jarFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在文件系统中查找类
     * 利用IO流遍历文件夹查找
     * 倘若注入了注解列表，则只返回注解列表中的注解了的类
     */
    public static void findClassesInFileSystemPackage(String packageName, String packagePath, List<Class<?>> classes, ArrayList<Class<? extends Annotation>> anno) {
        //获取包的物理路径
        File directory = new File(packagePath);
        if (!directory.exists()) {
            return;
        }
        //获取包下的所有文件
        File[] files = directory.listFiles();
        if (files == null) return;
        //遍历文件
        for (File file : files) {
            //如果是文件夹，则递归查找
            if (file.isDirectory()) {
                findClassesInFileSystemPackage(packageName + "." + file.getName(), file.getAbsolutePath(), classes, anno);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                try {
                    //如果注解列表不为空，则只返回注解列表中的注解了的类
                    isAnnotated(classes, anno, className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断注解列表是否为空
     * 如果不为空，则判断类是否有注解，加入存在注解的类
     * 如果没有注解，则直接加入列表
     */
    private static void isAnnotated(List<Class<?>> classes, ArrayList<Class<? extends Annotation>> anno, String className) throws ClassNotFoundException {
        if (anno != null) {
            Class<?> clazz = Class.forName(className);
            for (Class<? extends Annotation> aClass : anno)
                if (clazz.isAnnotationPresent(aClass)) {
                    classes.add(clazz);
                    break;
                }
        } else {
            //直接载入
            classes.add(Class.forName(className));
        }
    }

    /**
     * 将类列表转化为注解列表
     * ArrayList<Class<?>> -> ArrayList<Class<? extends Annotation>>
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Class<? extends Annotation>> toAnnotationList(ArrayList<Class<?>> classes) {
        ArrayList<Class<? extends Annotation>> annotations = new ArrayList<>();
        for (Class<?> aClass : classes) {
            //判断是否是注解类
            if (aClass.isAnnotation()) annotations.add((Class<? extends Annotation>) aClass);
        }
        return annotations;
    }

    /**
     * 用于判断扫描注解包的时候判断是文件系统还是JAR文件
     */
    public static void getPackClass(String packageName, ArrayList<Class<?>> scanResult) {
        try {
            //将包名转换为路径名
            String path = packageName.replace(".", "/");
            //获取类加载器
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //获取指定包名下的所有资源
            Enumeration<URL> resources = classLoader.getResources(path);
            //遍历资源
            while (resources.hasMoreElements()) {
                //通过URL的getProtocol()方法判断是文件系统还是JAR文件
                URL url = resources.nextElement();
                if (url.getProtocol().equals("file"))
                    ScannerTool.findClassesInFileSystemPackage(packageName, url.getFile(), scanResult, null);
                else if (url.getProtocol().equals("jar"))
                    ScannerTool.findClassesInJarPackage(packageName, url.getPath().substring(5, url.getPath().indexOf("!")), scanResult, null);
            }
        } catch (Exception e) {
            System.out.println("扫描包的过程中出现异常");
            e.printStackTrace();
        }
    }

    /**
     * 获取方法中所有参数的值
     * 添加为HashMap<String,String>
     */
    public static HashMap<String, String> getAnnoString(Class<? extends Annotation> anno, Method method) {
        HashMap<String, String> map = new HashMap<>();
        //获取注解的实例化
        Annotation annotation = method.getAnnotation(anno);
        //拼接注解的所有属性，利用/分割
        Method[] methods1 = anno.getDeclaredMethods();
        for (Method m : methods1) {
            try {
                Object value = m.invoke(annotation);
                map.put(m.getName(), value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
