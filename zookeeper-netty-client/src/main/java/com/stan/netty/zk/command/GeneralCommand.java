package com.stan.netty.zk.command;

import com.stan.netty.zk.provider.channel.ChannelProvider;
import com.stan.netty.zk.transport.UnprocessedRequest;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: 登录之外的其余命令
 */
public interface GeneralCommand {

    void loginFirst();

    String host();

    int port();

    ChannelProvider channelProvider();

    UnprocessedRequest unprocessedRequest();
}
