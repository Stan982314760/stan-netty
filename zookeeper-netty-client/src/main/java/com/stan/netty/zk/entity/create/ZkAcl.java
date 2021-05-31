package com.stan.netty.zk.entity.create;

import lombok.Data;

import java.io.Serializable;

/**
 * ACL模型
 */
@Data
public class ZkAcl implements Serializable {

    private int perms;

    private String scheme;

    private String id;

}
