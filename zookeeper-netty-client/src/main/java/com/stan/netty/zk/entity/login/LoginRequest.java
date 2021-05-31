package com.stan.netty.zk.entity.login;

import com.stan.netty.zk.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: stan
 * @Date: 2021/05/27
 * @Description: 登录请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Request {

    private Integer protocolVersion;

    private Long lastZxidSeen;

    private int timeout;

    private Long sessionId;

    private String password;

    private boolean readOnly = true;

    public static LoginRequest createRequest() {
        return new LoginRequest(0, (long) 0, 3000, (long) 0, "", true);
    }
}
