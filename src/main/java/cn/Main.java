package cn;

import cn.scanner.ScannerApplication;

/**
 * 程序的模拟入口，这个模块最主要的做注解和类:函数地址的获取，
 * 目前设计如下：
 * 类扫描器：扫描所有的类，获取类的注解，获取类的函数，获取函数的注解
 * 包装器：将类和函数的注解和函数地址包装成一个对象
 * 可用检测器：检测类和函数的注解，检测类和函数是否可用
 * 最终包装效果为
 * {
 * "触发类型": {
 * "触发内容”:“函数地址”
 * }
 */
public class Main {
    public static void main(String[] args) {

    }
}
