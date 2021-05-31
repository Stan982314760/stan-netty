package com.stan.netty.zk.provider.channel;

import com.stan.netty.zk.provider.Provider;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * @Author: stan
 * @Date: 2021/05/27
 * @Description: 获取Channel
 */
public interface ChannelProvider extends Provider {

    Channel getChannel(InetSocketAddress socketAddress);

}
