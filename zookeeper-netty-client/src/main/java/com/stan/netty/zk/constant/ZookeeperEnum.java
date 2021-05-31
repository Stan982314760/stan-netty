package com.stan.netty.zk.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: ZK的操作枚举
 */
@Getter
@AllArgsConstructor
public enum ZookeeperEnum {

    LOGIN("zk-login"),
    CREATE("zk-create"),
    CHILDREN("zk-children"),
    ;


    private final String name;
}
