package com.stan.netty.zk.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: 节点特性枚举
 */
@AllArgsConstructor
@Getter
public enum CreateModeEnum {
    /**
     * 永久性节点
     */
    PERSISTENT (0),
    /**
     * 永久性顺序节点
     */
    PERSISTENT_SEQUENTIAL (2),
    /**
     * 临时节点
     */
    EPHEMERAL (1),
    /**
     *  临时顺序节点
     */
    EPHEMERAL_SEQUENTIAL (3)

    ;

    private final int flag;
}
