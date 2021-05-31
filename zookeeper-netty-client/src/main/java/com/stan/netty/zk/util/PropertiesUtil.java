package com.stan.netty.zk.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 读取Properties文件
 */
@Slf4j
public final class PropertiesUtil {

    private static final Map<String, Properties> PROPERTIES_MAP = new HashMap<>();

    public static Properties loadProperties(String fileName) {
        Properties properties = PROPERTIES_MAP.get(fileName);
        if (properties == null) {
            try {
                InputStream inputStream =
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                properties = new Properties();
                properties.load(inputStream);
                PROPERTIES_MAP.put(fileName, properties);
            } catch (IOException e) {
               log.error("加载配置文件[{}]出错", fileName);
            }
        }

        return properties;
    }

}
