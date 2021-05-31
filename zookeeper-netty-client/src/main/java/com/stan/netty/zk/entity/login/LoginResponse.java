package com.stan.netty.zk.entity.login;

import com.stan.netty.zk.entity.Response;
import lombok.Data;

/**
 * @Author: stan
 * @Date: 2021/05/27
 * @Description: 登录响应模型
 */
@Data
public class LoginResponse implements Response {

    private Integer protocolVersion;

    private int timeout;

    private Long sessionId;

    private String password;

    private boolean readOnly;
}
