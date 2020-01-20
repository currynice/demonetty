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

    Byte CREATE_GROUP_CHAT_REQUEST = 3;
}
