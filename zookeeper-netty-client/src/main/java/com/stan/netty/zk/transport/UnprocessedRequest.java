package com.stan.netty.zk.transport;

import com.stan.netty.zk.constant.ZookeeperEnum;
import com.stan.netty.zk.entity.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: 缓存尚未进行处理的请求 正常情况下应该缓存的是请求ID对应的响应结果
 * 对于登录来说还没 XID 所以放一个登录的标示
 */
@Slf4j
public class UnprocessedRequest {

    private final Map<String, CompletableFuture<Response>> futureMap;
    private final AtomicBoolean ifLogin = new AtomicBoolean(false);

    public UnprocessedRequest() {
        this.futureMap = new ConcurrentHashMap<>();
    }

    public void put(String requestId, CompletableFuture<Response> future) {
        futureMap.put(requestId, future);
    }

    public void complete(String requestId, Response response) {
        CompletableFuture<Response> completableFuture = futureMap.remove(requestId);
        if (completableFuture == null) {
            throw new RuntimeException("当前Session未缓存" + requestId + "对应的future对象");
        }
        if (ZookeeperEnum.LOGIN.equals(requestId)) {
            ifLogin.compareAndSet(false, true);
        }
        completableFuture.complete(response);
    }

    public boolean existsRequest(String requestId) {
        return futureMap.containsKey(requestId);
    }


    public boolean ifLogin() {
        return ifLogin.get();
    }

}
