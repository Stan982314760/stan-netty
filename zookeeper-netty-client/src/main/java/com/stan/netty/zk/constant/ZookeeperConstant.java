package com.stan.netty.zk.constant;

/**
 * @Author: stan
 * @Date: 2021/05/27
 * @Description:
 */
public class ZookeeperConstant {

    /**
     * 登录请求的消息头字节长度
     */
    public static final int REQUEST_HEADER_LENGTH = 4;

    /**
     * 登录响应的消息体最小长度
     */
    public static final int LOGIN_RESP_MIN_LENGTH = 25;

    /**
     * CREATE 响应体消息最小长度
     */
    public static final int CREATE_RESP_MIN_LENGTH = 24;

    /**
     * zk的配置文件
     */
    public static final String ZK_FILE_NAME = "zk.properties";

    /**
     * zk默认host
     */
    public static final String DEFAULT_HOST = "192.168.138.128";


    /**
     * zk默认port
     */
    public static final String DEFAULT_PORT = "2181";

    /**
     * 登录编码器名称
     */
    public static final String LOGIN_ENCODER_NAME = "loginEncoder";

    /**
     * CREATE编码器名称
     */
    public static final String CREATE_ENCODER_NAME = "createEncoder";


    /**
     * 登录解码器名称
     */
    public static final String LOGIN_DECODER_NAME = "loginDecoder";

    /**
     * CREATE解码器名称
     */
    public static final String CREATE_DECODER_NAME = "createDecoder";


    /**
     * 登录处理器名称
     */
    public static final String LOGIN_HANDLER_NAME = "loginHandler";


    /**
     * CREATE处理器名称
     */
    public static final String CREATE_HANDLER_NAME = "createHandler";

}
