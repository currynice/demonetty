package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import java.util.List;

import static com.cxy.demonetty.procotol.packet.Command.CREATE_GROUP_REQUEST;


@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;


    @Override
    public Byte packetCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
