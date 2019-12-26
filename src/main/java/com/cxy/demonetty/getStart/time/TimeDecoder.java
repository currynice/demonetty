package com.cxy.demonetty.getStart.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;


/**
 *
 *   ChannelHandlerA无法处理更复杂的协议。
 *   可以把多个简单的ChannelHandler添加到ChannelPipeline
 *   比如一个处理碎片问题的TimeDecoder，以及TimeClientHandler的简单逻辑。
 *   Netty提供了一个可扩展的类ByteToMessageDecoder
 *
 *
 *  (1)ByteToMessageDecoder是ChannelInboundHandler的一个实现，使得处理碎片问题变得很容易。
 *  (2)每当接收到新的数据时，ByteToMessageDecoder都会使用内部维护的累积缓冲区来调用decode()方法。
 *  (3)decode()决定在累积缓冲区中没有足够数据的地方不添加任何内容。当接收到新数据时，ByteToMessageDecoder将再次调用decode()。
 *  (4)如果decode()将一个对象添加到out中，则意味着解码器成功地解码了一条消息。ByteToMessageDecoder将放弃累积缓冲区的读取部分。
 *                      ByteToMessageDecoder将继续调用decode()方法，直到它没有添加任何内容为止。
 */
public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        out.add(in.readBytes(4)); // (4)
    }
}