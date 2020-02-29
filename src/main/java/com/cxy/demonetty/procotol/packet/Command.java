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

    /**
     * 退出请求
     */
    Byte LOGOUT_REQUEST = 5;

    /**
     * 退出响应
     */
    Byte LOGOUT_RESPONSE = 6;

    /**
     * 创建群聊请求
     */
    Byte CREATE_GROUP_REQUEST = 7;

    /**
     * 创建群聊响应
     */
    Byte CREATE_GROUP_RESPONSE = 8;


    /**
     * 加入群聊请求
     */
    Byte JOIN_GROUP_REQUEST = 9;



    /**
     * 加入群聊响应
     */
    Byte JOIN_GROUP_RESPONSE = 10;




    /**
     * 获取群成员请求
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 11;



    /**
     *  获取群成员响应
     */
    Byte LIST_GROUP_MEMBERS_RESPONSE = 12;


    /**
     * 退出群请求
     */
    Byte QUIT_GROUP_REQUEST = 13;



    /**
     *  退出群响应
     */
    Byte QUIT_GROUP_RESPONSE = 14;



    /**
     * 发送群消息请求
     */
    Byte GROUP_MESSAGE_REQUEST = 15;


    /**
     *  群消息响应
     */
    Byte GROUP_MESSAGE_RESPONSE = 16;


    /**
     * 发送心跳包请求
     */
    Byte HEARTBEAT_REQUEST = 17;


    /**
     *  心跳包响应
     */
    Byte HEARTBEAT_RESPONSE = 18;
}
