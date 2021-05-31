package com.stan.netty.zk.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: 简单唯一ID生成
 */
public final class IdGeneratorUtil {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static int generate() {
        return COUNTER.getAndIncrement();
    }
}
