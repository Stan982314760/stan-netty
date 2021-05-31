package com.stan.netty.zk.codec.create;

import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.entity.create.CreateResponse;
import com.stan.netty.zk.transport.UnprocessedRequest;
import com.stan.netty.zk.util.SingletonFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: CREATE 处理器
 */
@Slf4j
public class CreateHandler extends SimpleChannelInboundHandler<CreateResponse> {

    private final UnprocessedRequest unprocessedRequest;

    public CreateHandler() {
        this.unprocessedRequest = SingletonFactory.getSingleton(UnprocessedRequest.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateResponse msg) throws Exception {
        if (msg != null && unprocessedRequest.existsRequest(msg.getXid() + "")) {
            log.info("create success, msg is {}", msg);
            unprocessedRequest.complete(msg.getXid() + "", msg);

            log.info("start removing handler bound to create command");
            ctx.pipeline().remove(ZookeeperConstant.CREATE_DECODER_NAME);
            ctx.pipeline().remove(ZookeeperConstant.CREATE_ENCODER_NAME);
            ctx.pipeline().remove(ZookeeperConstant.CREATE_HANDLER_NAME);
        }

    }
}
