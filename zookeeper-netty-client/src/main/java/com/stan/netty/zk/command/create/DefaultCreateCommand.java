package com.stan.netty.zk.command.create;

import com.stan.netty.zk.codec.create.CreateDecoder;
import com.stan.netty.zk.codec.create.CreateEncoder;
import com.stan.netty.zk.codec.create.CreateHandler;
import com.stan.netty.zk.command.AbstractGeneralCommand;
import com.stan.netty.zk.entity.Response;
import com.stan.netty.zk.entity.create.CreateRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static com.stan.netty.zk.constant.ZookeeperConstant.*;

/**
 * @Author: stan
 * @Date: 2021/05/29
 * @Description: 登录命令
 */
@Slf4j
public class DefaultCreateCommand extends AbstractGeneralCommand implements CreateCommand {

    public DefaultCreateCommand() {
        super();
    }


    @Override
    public CompletableFuture<Response> create(CreateRequest request) {
        Channel channel = channelProvider().getChannel(new InetSocketAddress(host(), port()));

        log.info("start appending handler bound to create");
        channel.pipeline()
                .addLast(CREATE_DECODER_NAME, new CreateDecoder())
                .addLast(CREATE_ENCODER_NAME, new CreateEncoder())
                .addLast(CREATE_HANDLER_NAME, new CreateHandler());

        CompletableFuture<Response> future = new CompletableFuture<>();
        unprocessedRequest().put(request.getXid() + "", future);

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
