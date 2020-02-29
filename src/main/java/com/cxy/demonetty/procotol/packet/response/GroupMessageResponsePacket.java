package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.IM.session.Session;
import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.GROUP_MESSAGE_RESPONSE;


@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte packetCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
