package com.stan.netty.zk;

import com.stan.netty.zk.command.children.ChildrenCommand;
import com.stan.netty.zk.command.children.DefaultChildrenCommand;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.children.ChildrenRequest;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: getChildren 测试
 */
public class ChildrenTest {

    @Test
    public void testGetChildren() throws ExecutionException, InterruptedException {
        ChildrenCommand childrenCommand = new DefaultChildrenCommand();
        ChildrenRequest request2 = ChildrenRequest.createChildrenRequest("/zookeeper");
        CompletableFuture<Response> future2 = childrenCommand.getChildren(request2);
        future2.get();
    }
}
