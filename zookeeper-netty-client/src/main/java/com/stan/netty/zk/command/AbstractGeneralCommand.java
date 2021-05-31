package com.stan.netty.zk.command;

import com.stan.netty.zk.command.login.DefaultLoginCommand;
import com.stan.netty.zk.command.login.LoginCommand;
import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.login.LoginRequest;
import com.stan.netty.zk.provider.channel.ChannelProvider;
import com.stan.netty.zk.provider.channel.impl.DefaultChannelProvider;
import com.stan.netty.zk.transport.UnprocessedRequest;
import com.stan.netty.zk.util.PropertiesUtil;
import com.stan.netty.zk.util.SingletonFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.stan.netty.zk.constant.ZookeeperConstant.ZK_FILE_NAME;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: GeneralCommand抽象基类
 */
@Slf4j
public abstract class AbstractGeneralCommand implements GeneralCommand {

    private final LoginCommand loginCommand;
    private final UnprocessedRequest unprocessedRequest;
    private final ChannelProvider channelProvider;
    private final int port;
    private final String host;

    public AbstractGeneralCommand() {
        this.loginCommand = SingletonFactory.getSingleton(DefaultLoginCommand.class);
        this.unprocessedRequest = SingletonFactory.getSingleton(UnprocessedRequest.class);
        this.channelProvider = SingletonFactory.getSingleton(DefaultChannelProvider.class);
        this.port = Integer.valueOf((String) PropertiesUtil.loadProperties(ZookeeperConstant.ZK_FILE_NAME)
                .getOrDefault("port", "2181"));
        this.host = (String) PropertiesUtil.loadProperties(ZK_FILE_NAME)
                .getOrDefault("host", "192.168.138.128");

        loginFirst();
    }

    @Override
    public void loginFirst() {
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
    public String host() {
        return this.host;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public ChannelProvider channelProvider() {
        return this.channelProvider;
    }

    @Override
    public UnprocessedRequest unprocessedRequest() {
        return this.unprocessedRequest;
    }
}
