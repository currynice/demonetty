package com.cxy.demonetty.procotol.packet.request;


import com.cxy.demonetty.procotol.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import static com.cxy.demonetty.procotol.packet.Command.MESSAGE_REQUEST;

/**
 * [客户端向服务端发送消息]数据包
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestPacket extends Packet implements Serializable {


    /**
     * 目标用户id
     */
    private String toUserId;


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

        return MESSAGE_REQUEST;
    }


    public MessageRequestPacket(String message) {
        this.message = message;
    }
}
