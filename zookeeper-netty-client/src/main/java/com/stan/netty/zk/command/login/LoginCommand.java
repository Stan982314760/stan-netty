package com.stan.netty.zk.command.login;

import com.stan.netty.zk.entity.Request;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.command.Command;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description:
 */
public interface LoginCommand extends Command {

    CompletableFuture<Response> login(Request request);


}
