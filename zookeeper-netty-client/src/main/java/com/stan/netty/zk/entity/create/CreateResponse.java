package com.stan.netty.zk.entity.create;

import com.stan.netty.zk.entity.AbstractResponse;
import lombok.Data;

/**
 * 具体查看 https://juejin.cn/post/6844903999854870535#heading-12
 *
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: CREATE 命令响应体
 */
@Data
public class CreateResponse extends AbstractResponse {
    private String path;
}
