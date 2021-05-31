package com.stan.netty.zk.command.create;

import com.stan.netty.zk.command.Command;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.create.CreateRequest;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: CREATE 命令接口
 */
public interface CreateCommand extends Command {

    CompletableFuture<Response> create(CreateRequest request);
}
