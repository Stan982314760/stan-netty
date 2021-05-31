package com.stan.netty.zk.entity.children;

import com.stan.netty.zk.constant.OpCode;
import com.stan.netty.zk.entity.AbstractRequest;
import com.stan.netty.zk.util.IdGeneratorUtil;
import lombok.Data;

/**
 * 具体查看 https://juejin.cn/post/6844903999854870535#heading-12
 *
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: getChildren 的请求报文
 */
@Data
public class ChildrenRequest extends AbstractRequest {

    private String path;
    private boolean watch;

    public static ChildrenRequest createChildrenRequest(String path) {
        ChildrenRequest request = new ChildrenRequest();
        request.setPath(path);
        request.setWatch(false);
        request.setType(OpCode.GET_CHILDREN);
        request.setXid(IdGeneratorUtil.generate());
        return request;
    }
}
