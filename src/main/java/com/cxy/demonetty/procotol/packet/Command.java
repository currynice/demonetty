package com.cxy.demonetty.procotol.packet;

/**
 * 所有指令常量
 */
public interface Command {

    /**
     * 登录请求指令
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登录响应指令
     */
    Byte LOGIN_RESPONSE = 2;

    /**
     * 客户端向服务端发送消息指令
     */
    Byte MESSAGE_REQUEST = 3;

    /**
     * 服务端向客户端发送消息指令
     */
    Byte MESSAGE_RESPONSE = 4;

//    Byte CREATE_GROUP_CHAT_REQUEST = 3;
}
