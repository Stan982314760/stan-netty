package com.stan.netty.zk.entity;

import lombok.Data;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 抽象响应模型
 */
@Data
public abstract class AbstractResponse implements Response {

    private int xid;
    private long zXid;
    private int errorCode;
}
