package com.cxy.demonetty.procotol.packet.codec;

import com.cxy.demonetty.procotol.packet.Packet;
import com.cxy.demonetty.procotol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 *  编码器：对象封装为数据包
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodeC.INSTANCE().encode(byteBuf,packet);
    }
}
