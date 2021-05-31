package com.stan.netty.zk.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: stan
 * @Date: 2021/05/24
 * @Description: Zookeeper客户端
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    private final int connectionTimeout;
    private final String password;

    public NettyClient() {
        this(3000, "");
    }


    public NettyClient(int connectionTimeout, String password) {
        this.connectionTimeout = connectionTimeout;
        this.password = password;
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        init();
    }

    private void init() {
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // pipeLine放到响应的Command操作里面去初始化
                    }
                });
    }

    @SneakyThrows
    public Channel doConnect(InetSocketAddress socketAddress) {
        CompletableFuture<Channel> future = new CompletableFuture<>();
        bootstrap.connect(socketAddress).addListener((ChannelFuture f) -> {
            if (f.isSuccess()) {
                log.info("connect to server success!!");
                future.complete(f.channel());
            } else {
                throw new RuntimeException("connect fail!!");
            }
        });

        return future.get();
    }

}
