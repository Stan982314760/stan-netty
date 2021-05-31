package com.stan.netty.zk.entity.children;

import com.stan.netty.zk.entity.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * 具体查看 https://juejin.cn/post/6844903999854870535#heading-12
 *
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: getChildren 的响应报文
 */
@Data
public class ChildrenResponse extends AbstractResponse {

    private List<String> childrenPath;

}
