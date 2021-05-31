package com.stan.netty.zk;

import com.stan.netty.zk.command.login.DefaultLoginCommand;
import com.stan.netty.zk.command.login.LoginCommand;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.login.LoginRequest;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 登录单元测试
 */
public class LoginTest {

    @Test
    public void testLogin() throws ExecutionException, InterruptedException {
        LoginCommand command = new DefaultLoginCommand();
        LoginRequest request = LoginRequest.createRequest();
        CompletableFuture<Response> future = command.login(request);
        future.get();
    }
}
