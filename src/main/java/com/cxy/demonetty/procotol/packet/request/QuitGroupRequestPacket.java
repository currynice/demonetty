package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.QUIT_GROUP_REQUEST;


@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte packetCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
