package com.stan.netty.zk.command.children;

import com.stan.netty.zk.command.Command;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.children.ChildrenRequest;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: getChildren command
 */
public interface ChildrenCommand extends Command {

    CompletableFuture<Response> getChildren(ChildrenRequest request);
}
