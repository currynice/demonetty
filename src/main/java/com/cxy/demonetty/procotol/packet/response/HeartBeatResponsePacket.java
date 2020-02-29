package com.cxy.demonetty.procotol.packet.response;

import com.cxy.demonetty.procotol.packet.Packet;

import static com.cxy.demonetty.procotol.packet.Command.HEARTBEAT_RESPONSE;


public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte packetCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
