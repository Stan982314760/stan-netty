package com.stan.netty.zk.codec.login;

import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.entity.login.LoginResponse;
import com.stan.netty.zk.constant.ZookeeperEnum;
import com.stan.netty.zk.transport.UnprocessedRequest;
import com.stan.netty.zk.util.SingletonFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 登录处理器
 */
@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponse> {

    private final UnprocessedRequest unprocessedRequest;

    public LoginHandler() {
        this.unprocessedRequest = SingletonFactory.getSingleton(UnprocessedRequest.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        if (msg != null && unprocessedRequest.existsRequest(ZookeeperEnum.LOGIN.name())) {
            log.info("login to zk success!!, the resp is {}", msg);
            unprocessedRequest.complete(ZookeeperEnum.LOGIN.name(), msg);

            log.info("start removing handler bound to login command");
            ctx.pipeline().remove(ZookeeperConstant.LOGIN_DECODER_NAME);
            ctx.pipeline().remove(ZookeeperConstant.LOGIN_ENCODER_NAME);
            ctx.pipeline().remove(ZookeeperConstant.LOGIN_HANDLER_NAME);
        }

    }
}
