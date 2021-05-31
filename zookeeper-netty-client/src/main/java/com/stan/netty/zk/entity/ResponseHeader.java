package com.stan.netty.zk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 响应content的header
 */
@Data
public class ResponseHeader implements Serializable {
    private int xid;
    private int type;
}
