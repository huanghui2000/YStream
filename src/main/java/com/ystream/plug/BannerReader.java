package com.ystream.plug;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Banner类
 * 默认使用的是 https://www.bootschool.net/ascii的 starWars风格
 */
@Slf4j
public class BannerReader {
    /*
     * 读取Banner并且绘制
     */
    public void printBanner() {
        try {
            //输出资源文件下的banner.txt文件
            InputStream files = getClass().getClassLoader().getResourceAsStream("banner.txt");
            // 获取资源文件的输入流
            InputStream inputStream = files;
            if (inputStream != null) {
                // 使用BufferedReader逐行读取文件内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            log.error("读取Banner时发生了错误");
        }
    }

}
