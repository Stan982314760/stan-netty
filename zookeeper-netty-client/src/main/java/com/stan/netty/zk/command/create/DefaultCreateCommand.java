package com.stan.netty.zk.command.create;

import com.stan.netty.zk.codec.create.CreateDecoder;
import com.stan.netty.zk.codec.create.CreateEncoder;
import com.stan.netty.zk.codec.create.CreateHandler;
import com.stan.netty.zk.command.login.DefaultLoginCommand;
import com.stan.netty.zk.command.login.LoginCommand;
import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.create.CreateRequest;
import com.stan.netty.zk.entity.login.LoginRequest;
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
import java.util.concurrent.ExecutionException;

import static com.stan.netty.zk.constant.ZookeeperConstant.*;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: 登录命令
 */
@Slf4j
public class DefaultCreateCommand implements CreateCommand {

    private final UnprocessedRequest unprocessedRequest;
    private final ChannelProvider channelProvider;
    private final int port;
    private final String host;
    private final LoginCommand loginCommand;

    public DefaultCreateCommand() {
        this.loginCommand = SingletonFactory.getSingleton(DefaultLoginCommand.class);
        this.unprocessedRequest = SingletonFactory.getSingleton(UnprocessedRequest.class);
        this.channelProvider = SingletonFactory.getSingleton(DefaultChannelProvider.class);
        this.port = Integer.valueOf((String) PropertiesUtil.loadProperties(ZookeeperConstant.ZK_FILE_NAME)
                .getOrDefault("port", "2181"));
        this.host = (String) PropertiesUtil.loadProperties(ZK_FILE_NAME)
                .getOrDefault("host", "192.168.138.128");

        loginFirst();
    }

    private void loginFirst() {
        if (!unprocessedRequest.ifLogin()) {
            CompletableFuture<Response> future = loginCommand.login(LoginRequest.createRequest());
            try {
                future.get(); // 阻塞等待登录成功
            } catch (InterruptedException | ExecutionException e) {
                log.error("login error", e);
            }
        }

    }


    @Override
    public CompletableFuture<Response> create(CreateRequest request) {
        Channel channel = channelProvider.getChannel(new InetSocketAddress(host, port));

        log.info("start appending handler bound to create");
        channel.pipeline()
                .addLast(CREATE_DECODER_NAME, new CreateDecoder())
                .addLast(CREATE_ENCODER_NAME, new CreateEncoder())
                .addLast(CREATE_HANDLER_NAME, new CreateHandler());

        CompletableFuture<Response> future = new CompletableFuture<>();
        unprocessedRequest.put(request.getXid() + "", future);

        channel.writeAndFlush(request).addListener((ChannelFuture f) -> {
            if (f.isSuccess()) {
                log.info("send create request success!!");
            } else {
                log.error("send create request error!!");
                f.channel().close();
            }
        });

        return future;
    }
}
