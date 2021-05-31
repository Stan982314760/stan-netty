package com.stan.netty.zk.codec.children;

import com.stan.netty.zk.entity.children.ChildrenRequest;
import com.stan.netty.zk.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static com.stan.netty.zk.constant.ZookeeperConstant.REQUEST_HEADER_LENGTH;

/**
 * @Author: stan
 * @Date: 2021/05/31
 * @Description: 获取子节点请求 编码器
 */
@Slf4j
public class ChildrenEncoder extends MessageToByteEncoder<ChildrenRequest> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ChildrenRequest msg, ByteBuf out) throws Exception {
        // prepare length header
        out.markWriterIndex();
        out.writerIndex(out.writerIndex() + REQUEST_HEADER_LENGTH);

        out.writeInt(msg.getXid()); // xid
        out.writeInt(msg.getType()); // type(opCode)
        ByteBufUtil.writeStr(msg.getPath(), out); // path
        out.writeBoolean(msg.isWatch()); // watch

        int frameLength = out.writerIndex();
        out.resetWriterIndex();
        out.writeInt(frameLength - REQUEST_HEADER_LENGTH);
        out.writerIndex(frameLength);
    }
}
