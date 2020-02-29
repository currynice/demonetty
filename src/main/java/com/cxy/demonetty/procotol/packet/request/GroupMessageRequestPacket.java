package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.cxy.demonetty.procotol.packet.Command.GROUP_MESSAGE_REQUEST;


@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte packetCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
