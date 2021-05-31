package com.stan.netty.zk.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 单例工厂
 */
@Slf4j
public final class SingletonFactory {

    private static final Map<Class<?>, Object> OBJECT_MAP = new ConcurrentHashMap<>();


    public static <T> T getSingleton(Class<T> clazz) {
        try {
            Object obj = OBJECT_MAP.get(clazz);
            if (obj == null) {
                synchronized (SingletonFactory.class) {
                    obj = clazz.newInstance();
                    OBJECT_MAP.put(clazz, obj);
                }
            }

            return clazz.cast(obj);
        } catch (Exception e) {
            log.error("create [{}] error", clazz, e);
        }

        return null;
    }

}
