package com.stan.netty.zk.command.children;

import com.stan.netty.zk.codec.children.ChildrenDecoder;
import com.stan.netty.zk.codec.children.ChildrenEncoder;
import com.stan.netty.zk.codec.children.ChildrenHandler;
import com.stan.netty.zk.command.AbstractGeneralCommand;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.children.ChildrenRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static com.stan.netty.zk.constant.ZookeeperConstant.*;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: ChildrenCommand默认实现
 */
@Slf4j
public class DefaultChildrenCommand extends AbstractGeneralCommand implements ChildrenCommand {


    public DefaultChildrenCommand() {
       super();
    }


    @Override
    public CompletableFuture<Response> getChildren(ChildrenRequest request) {
        Channel channel = channelProvider().getChannel(new InetSocketAddress(host(), port()));

        log.info("start appending handler bound to getChildren");
        channel.pipeline()
                .addLast(CHILDREN_DECODER_NAME, new ChildrenDecoder())
                .addLast(CHILDREN_ENCODER_NAME, new ChildrenEncoder())
                .addLast(CHILDREN_HANDLER_NAME, new ChildrenHandler());

        CompletableFuture<Response> future = new CompletableFuture<>();
        unprocessedRequest().put(request.getXid() + "", future);

        channel.writeAndFlush(request).addListener((ChannelFuture f) -> {
            if (f.isSuccess()) {
                log.info("send getChildren request success!!");
            } else {
                log.error("send getChildren request error!!");
                f.channel().close();
            }
        });

        return future;
    }
}
