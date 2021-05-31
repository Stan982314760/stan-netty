package com.stan.netty.zk.command.login;

import com.stan.netty.zk.codec.login.LoginDecoder;
import com.stan.netty.zk.codec.login.LoginEncoder;
import com.stan.netty.zk.codec.login.LoginHandler;
import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.constant.ZookeeperEnum;
import com.stan.netty.zk.entity.Request;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.provider.channel.ChannelProvider;
import com.stan.netty.zk.provider.channel.impl.DefaultChannelProvider;
import com.stan.netty.zk.transport.UnprocessedRequest;
import com.stan.netty.zk.util.PropertiesUtil;
import com.stan.netty.zk.util.SingletonFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description:
 */
@Slf4j
public class DefaultLoginCommand implements LoginCommand {

    private final String host;
    private final int port;
    private final ChannelProvider channelProvider;
    private final UnprocessedRequest unprocessedRequest;


    public DefaultLoginCommand() {
        this.channelProvider = SingletonFactory.getSingleton(DefaultChannelProvider.class);
        this.unprocessedRequest = SingletonFactory.getSingleton(UnprocessedRequest.class);
        this.host = (String) PropertiesUtil.loadProperties(ZookeeperConstant.ZK_FILE_NAME).getOrDefault("host", "192.168.138.128");
        this.port = Integer.valueOf((String) PropertiesUtil.loadProperties(ZookeeperConstant.ZK_FILE_NAME).getOrDefault("port", "2181"));
    }


    @Override
    public CompletableFuture<Response> login(Request request) {
        Channel channel = channelProvider.getChannel(new InetSocketAddress(host, port));

        log.info("start appending handler bound to login");
        channel.pipeline()
                .addLast(ZookeeperConstant.LOGIN_DECODER_NAME, new LoginDecoder())
                .addLast(ZookeeperConstant.LOGIN_ENCODER_NAME, new LoginEncoder())
                .addLast(ZookeeperConstant.LOGIN_HANDLER_NAME, new LoginHandler());

        CompletableFuture<Response> future = new CompletableFuture<>();
        unprocessedRequest.put(ZookeeperEnum.LOGIN.name(), future);

        channel.writeAndFlush(request).addListener((ChannelFuture f) -> {
            if (f.isSuccess()) {
                log.info("send login request success");
            } else {
                log.error("send login request error");
                f.channel().close();
            }
        });

        return future;
    }


}
