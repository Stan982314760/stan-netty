package com.stan.netty.zk.util;

import com.stan.netty.zk.entity.create.ZkAcl;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: ByteBuf 工具类
 */
public final class ByteBufUtil {

    public static void writeStr(String data, ByteBuf byteBuf) {
        byte[] bytes = data.getBytes(CharsetUtil.UTF_8);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public static void writeAclList(List<ZkAcl> aclList, ByteBuf byteBuf) {
        byteBuf.writeInt(aclList.size());
        for (ZkAcl zkAcl : aclList) {
            byteBuf.writeInt(zkAcl.getPerms());
            writeStr(zkAcl.getScheme(), byteBuf);
            writeStr(zkAcl.getId(), byteBuf);
        }
    }

}
