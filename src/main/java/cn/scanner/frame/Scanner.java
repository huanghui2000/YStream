package cn.scanner.frame;

import java.util.ArrayList;

/**
 * 所有扫描器的接口
 * 所有的扫描器应该满足以下功能：
 * 1. 扫描所有的地址
 * scanAll()
 * 2. 返回一个包含所有扫描结果的列表
 * getScanResult()
 * <p>
 * 目前的扫描器有：
 * 1. 类扫描器
 * 2. 函数扫描器
 */
public interface Scanner {
    /**
     * 扫描所有的地址
     */
    void scanAll();

    /**
     * 返回一个包含所有扫描结果的列表
     */
    ArrayList<?> getScanResult();
}
