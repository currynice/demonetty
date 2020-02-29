package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte packetCommand() {

        return JOIN_GROUP_REQUEST;
    }
}
