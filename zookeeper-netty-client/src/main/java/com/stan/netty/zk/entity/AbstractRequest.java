package com.stan.netty.zk.entity;

import lombok.Data;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 抽象请求模型
 */
@Data
public abstract class AbstractRequest implements Request {

    private int xid;
    private int type;

}
