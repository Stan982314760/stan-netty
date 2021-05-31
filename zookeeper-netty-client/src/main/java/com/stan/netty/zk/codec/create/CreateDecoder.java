package com.stan.netty.zk.codec.create;

import com.stan.netty.zk.constant.ZookeeperConstant;
import com.stan.netty.zk.entity.create.CreateResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: CREATE响应体解码器
 */
@Slf4j
public class CreateDecoder extends LengthFieldBasedFrameDecoder {

    public CreateDecoder() {
        this(2048, 0, 4, 0, 4);
    }


    public CreateDecoder(
            int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        if (decode instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) decode;
            if (msg.readableBytes() >= ZookeeperConstant.CREATE_RESP_MIN_LENGTH) {
                try {
                    return decodeFrame(msg);
                } catch (Exception e) {
                    log.error("create decoder error", e);
                } finally {
                    msg.release();
                }
            }
        }

        return decode;
    }

    private Object decodeFrame(ByteBuf msg) {
        CreateResponse response = new CreateResponse();
        response.setXid(msg.readInt()); // xid
        response.setZXid(msg.readLong()); // zXid
        response.setErrorCode(msg.readInt()); // errorCode
        int pathLen = msg.readInt();
        if (pathLen > 0) {
            byte[] bytes = new byte[pathLen];
            msg.readBytes(bytes);
            response.setPath(new String(bytes, CharsetUtil.UTF_8));
        }

        return response;
    }
}
