package com.cxy.demonetty.procotol.packet.response;


import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;


import java.io.Serializable;

import static com.cxy.demonetty.procotol.packet.Command.LOGIN_RESPONSE;

/**
 * 服务端发送的[请求响应]数据包
 */
@Data
public class LoginResponsePacket extends Packet implements Serializable {

    private String userId;

    private String userName;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误说明
     */
    private String reason;





    /**
     * 返回指令
     * @return
     */
    @Override
    public Byte packetCommand() {

        return LOGIN_RESPONSE;
    }
}
