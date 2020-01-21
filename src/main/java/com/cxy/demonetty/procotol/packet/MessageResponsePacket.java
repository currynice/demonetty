package com.cxy.demonetty.procotol.packet;


import lombok.Data;

import java.io.Serializable;
import static com.cxy.demonetty.procotol.packet.Command.MESSAGE_RESPONSE;

/**
 * [客户端向服务端发送消息]数据包
 */
@Data
public class MessageResponsePacket extends Packet implements Serializable {



    /**
     * 消息
     */
    private String message;


    /**
     * 返回指令
     * @return
     */
    @Override
    public Byte packetCommand() {

        return MESSAGE_RESPONSE;
    }
}
