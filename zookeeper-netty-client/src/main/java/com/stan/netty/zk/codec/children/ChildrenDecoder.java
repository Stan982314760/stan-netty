package com.stan.netty.zk.codec.children;


import com.stan.netty.zk.entity.children.ChildrenResponse;
import com.stan.netty.zk.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.stan.netty.zk.constant.ZookeeperConstant.CHILDREN_RESP_MIN_LENGTH;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: 获取子节点请求 编码器
 */
@Slf4j
public class ChildrenDecoder extends LengthFieldBasedFrameDecoder {


    public ChildrenDecoder() {
        this(2048, 0, 4, 0, 4);
    }


    public ChildrenDecoder(
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
            if (msg.readableBytes() >= CHILDREN_RESP_MIN_LENGTH) {
                try {
                    return decodeFrame(msg);
                } catch (Exception e) {
                    log.error("Children Decoder error", e);
                } finally {
                    msg.release();
                }
            }
        }

        return frame;
    }

    private Object decodeFrame(ByteBuf msg) {
        ChildrenResponse response = new ChildrenResponse();
        response.setXid(msg.readInt());
        response.setZXid(msg.readLong());
        int errCode = msg.readInt();
        response.setErrorCode(errCode);

        if (errCode == 0) {
            int listLen = msg.readInt();
            if (listLen > 0) {
                List<String> list = new ArrayList<>(listLen);
                for (int i = 0; i < listLen; i++) {
                    list.add(ByteBufUtil.readStr(msg));
                }
                response.setChildrenPath(list);
            }
        }

        return response;
    }
}