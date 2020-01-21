package com.cxy.demonetty.procotol.packet;


import lombok.Data;

import java.io.Serializable;

import static com.cxy.demonetty.procotol.packet.Command.LOGIN_REQUEST;

/**
 * 客户端发送的[请求登录]数据包
 */
@Data
public class LoginRequestPacket extends Packet implements Serializable {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 返回指令
     * @return
     */
    @Override
    public Byte packetCommand() {

        return LOGIN_REQUEST;
    }
}
