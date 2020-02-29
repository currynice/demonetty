package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.JOIN_GROUP_RESPONSE;


@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte packetCommand() {

        return JOIN_GROUP_RESPONSE;
    }
}
