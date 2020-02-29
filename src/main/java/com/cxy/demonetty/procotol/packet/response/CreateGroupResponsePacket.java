package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.cxy.demonetty.procotol.packet.Command.CREATE_GROUP_RESPONSE;


@Data
public class CreateGroupResponsePacket extends Packet implements Serializable {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte packetCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
