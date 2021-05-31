package com.stan.netty.zk.entity.create;

import com.stan.netty.zk.constant.CreateModeEnum;
import com.stan.netty.zk.constant.OpCode;
import com.stan.netty.zk.entity.AbstractRequest;
import com.stan.netty.zk.util.IdGeneratorUtil;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 具体查看 https://juejin.cn/post/6844903999854870535#heading-12
 *
 * @Author: stan
 * @Date: 2021/05/28
 * @Description: CREATE 命令请求体
 */
@Data
public class CreateRequest extends AbstractRequest {

    private String path;
    private String data;
    private List<ZkAcl> aclList;
    private int flags;

    public static CreateRequest createRequest() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setXid(IdGeneratorUtil.generate());
        createRequest.setType(OpCode.CREATE);
        createRequest.setFlags(CreateModeEnum.EPHEMERAL.getFlag());

        ZkAcl acl = new ZkAcl();
        acl.setId("anyone");
        acl.setScheme("world");
        acl.setPerms(31);
        createRequest.setAclList(Collections.singletonList(acl));

        return createRequest;
    }
}
