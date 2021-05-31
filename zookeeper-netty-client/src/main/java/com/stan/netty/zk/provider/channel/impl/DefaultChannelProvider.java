package com.stan.netty.zk.provider.channel.impl;

import com.stan.netty.zk.provider.channel.ChannelProvider;
import com.stan.netty.zk.transport.NettyClient;
import com.stan.netty.zk.util.SingletonFactory;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: stan
 * @Date: 2021/05/27
 * @Description:
 */
public class DefaultChannelProvider implements ChannelProvider {

    private final Map<String, Channel> channelMap;
    private final NettyClient nettyClient;

    public DefaultChannelProvider() {
        channelMap = new ConcurrentHashMap<>();
        nettyClient = SingletonFactory.getSingleton(NettyClient.class);
    }


    @Override
    public Channel getChannel(InetSocketAddress socketAddress) {
        String address = socketAddress.toString();
        Channel channel = channelMap.get(address);
        if (channel == null || !channel.isActive()) {
            Channel newChannel = connectToChannel(socketAddress);
            channelMap.put(address, newChannel);
            return newChannel;
        }

        return channel;
    }

    private Channel connectToChannel(InetSocketAddress socketAddress) {
        return nettyClient.doConnect(socketAddress);
    }
}
