package com.cxy.demonetty.getStart.RWPOJO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 *
 */
public class MyTimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            System.out.println("暂时不足");
            return;
        }
        //修改 Decoder以生成MyTime而不是ByteBuf
        out.add(new MyTime(in.readUnsignedInt())); // (4)
    }
}