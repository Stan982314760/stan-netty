package com.stan.netty.zk.codec.children;

import com.stan.netty.zk.entity.children.ChildrenResponse;
import com.stan.netty.zk.transport.UnprocessedRequest;
import com.stan.netty.zk.util.SingletonFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: 获取子节点响应处理器
 */
@Slf4j
public class ChildrenHandler extends SimpleChannelInboundHandler<ChildrenResponse> {

    private final UnprocessedRequest unprocessedRequest;

    public ChildrenHandler() {
        this.unprocessedRequest = SingletonFactory.getSingleton(UnprocessedRequest.class);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChildrenResponse msg) throws Exception {
        if (msg != null && unprocessedRequest.existsRequest(msg.getXid() + "")) {
            log.info("getChildren success, result is [{}]", msg);
            unprocessedRequest.complete(msg.getXid() + "", msg);
        }

    }
}
