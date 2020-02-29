package com.cxy.demonetty.procotol.packet.request;

import com.cxy.demonetty.procotol.packet.Packet;

import static com.cxy.demonetty.procotol.packet.Command.HEARTBEAT_REQUEST;


public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte packetCommand() {
        return HEARTBEAT_REQUEST;
    }
}
