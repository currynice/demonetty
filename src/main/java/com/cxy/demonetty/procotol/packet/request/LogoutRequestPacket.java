package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte packetCommand() {

        return LOGOUT_REQUEST;
    }
}
