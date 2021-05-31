package com.stan.netty.zk.codec.login;

import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.entity.login.LoginResponse;
import com.stan.netty.zk.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 具体报文查看: https://juejin.cn/post/6844903999854870535
 *
 * @Author: stan
 * @Date: 2021/05/27
 * @Description: 登录解码器
 */
@Slf4j
public class LoginDecoder extends LengthFieldBasedFrameDecoder {

    public LoginDecoder() {
        this(2048, 0, 4, 0, 4);
    }


    public LoginDecoder(
            int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object frame = super.decode(ctx, in);
        if (frame instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) frame;
            if (msg.readableBytes() > ZookeeperConstant.LOGIN_RESP_MIN_LENGTH) {
                try {
                    return decodeFrame(msg);
                } catch (Exception e) {
                    log.error("login decoder error", e);
                } finally {
                    msg.release();
                }
            }
        }

        return frame;
    }

    private Object decodeFrame(ByteBuf msg) {
        LoginResponse response = new LoginResponse();
        response.setProtocolVersion(msg.readInt());
        response.setTimeout(msg.readInt());
        response.setSessionId(msg.readLong());
        response.setPassword(ByteBufUtil.readStr(msg));
        response.setReadOnly(msg.readBoolean());
        return response;
    }
}
