package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.procotol.packet.Packet;
import lombok.Data;

import static com.cxy.demonetty.procotol.packet.Command.LOGOUT_RESPONSE;


@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte packetCommand() {

        return LOGOUT_RESPONSE;
    }
}
