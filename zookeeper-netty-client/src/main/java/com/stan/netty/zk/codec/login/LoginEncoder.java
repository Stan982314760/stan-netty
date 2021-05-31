package com.stan.netty.zk.codec.login;

import com.stan.netty.zk.entity.login.LoginRequest;
import com.stan.netty.zk.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static com.stan.netty.zk.constant.ZookeeperConstant.REQUEST_HEADER_LENGTH;

/**
 * 具体报文查看: https://juejin.cn/post/6844903999854870535
 *
 * @Author: stan
 * @Date: 2021/05/27
 * @Description: 登录编码器
 */
@Slf4j
public class LoginEncoder extends MessageToByteEncoder<LoginRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LoginRequest request, ByteBuf out) throws Exception {
        // prepare space
        out.markWriterIndex();
        out.writerIndex(out.writerIndex() + REQUEST_HEADER_LENGTH);

        out.writeInt(request.getProtocolVersion());
        out.writeLong(request.getLastZxidSeen());
        out.writeInt(request.getTimeout());
        out.writeLong(request.getSessionId());
        ByteBufUtil.writeStr(request.getPassword(), out);
        out.writeBoolean(request.isReadOnly());

        // write header
        int frameLength = out.writerIndex();
        out.resetWriterIndex();
        out.writeInt(frameLength - REQUEST_HEADER_LENGTH);
        out.writerIndex(frameLength);

    }

}
