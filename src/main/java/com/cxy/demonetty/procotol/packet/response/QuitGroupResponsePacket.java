package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.QUIT_GROUP_RESPONSE;


@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte packetCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
