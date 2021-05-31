package com.stan.netty.zk;

import com.stan.netty.zk.command.create.CreateCommand;
import com.stan.netty.zk.command.create.DefaultCreateCommand;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.create.CreateRequest;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: CREATE 单元测试
 */
public class CreateTest {

    @Test
    public void testCreate() throws ExecutionException, InterruptedException {
        CreateCommand createCommand = new DefaultCreateCommand();
        CreateRequest request = CreateRequest.createRequest("/test", "stan");
        CompletableFuture<Response> future = createCommand.create(request);
        future.get();
    }
}
