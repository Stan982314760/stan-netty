package com.stan.netty.zk.codec.create;

import com.stan.netty.zk.entity.create.CreateRequest;
import com.stan.netty.zk.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static com.stan.netty.zk.constant.ZookeeperConstant.REQUEST_HEADER_LENGTH;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: CREATE请求体编码器
 */
@Slf4j
public class CreateEncoder extends MessageToByteEncoder<CreateRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CreateRequest request, ByteBuf out) throws Exception {
        // prepare for header
        out.markWriterIndex();
        out.writerIndex(out.writerIndex() + REQUEST_HEADER_LENGTH);

        out.writeInt(request.getXid()); // xid
        out.writeInt(request.getType()); // type
        ByteBufUtil.writeStr(request.getPath(), out); // path
        ByteBufUtil.writeStr(request.getData(), out); // data
        ByteBufUtil.writeAclList(request.getAclList(), out); // aclList
        out.writeInt(request.getFlags()); // flags

        int frameLength = out.writerIndex();
        out.resetWriterIndex();
        out.writeInt(frameLength - REQUEST_HEADER_LENGTH);
        out.writerIndex(frameLength);
    }
}
