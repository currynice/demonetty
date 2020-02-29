package com.cxy.demonetty.procotol.packet.codec;

import com.cxy.demonetty.procotol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;


/**
 * 解码器：数据包解析成对象
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                 list.add(PacketCodeC.INSTANCE().decode(byteBuf));
    }
}
