package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.LIST_GROUP_MEMBERS_REQUEST;


@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte packetCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
