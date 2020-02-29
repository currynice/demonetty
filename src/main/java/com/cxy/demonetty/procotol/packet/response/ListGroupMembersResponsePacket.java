package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.IM.session.Session;
import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import java.util.List;

import static com.cxy.demonetty.procotol.packet.Command.LIST_GROUP_MEMBERS_RESPONSE;


@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte packetCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
