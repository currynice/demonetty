package com.cxy.demonetty.getStart.RWPOJO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 时间编码器，继承模板模式MessageToByteEncoder，将UnixTime转换成ByteBuf。比编写解码器简单，因为在编码消息时不需要考虑包碎片。
 *
 * (1)首先，按原样传递原始的ChannelPromise，以便Netty在编码的数据实际写入线路时将其标记为成功或失败。()
 * 其次，不调用ctx.flush()。上层{@link ChannelOutboundHandlerAdapter#flush(ChannelHandlerContext)}，用于flush()操作。
 */
public class MyTimeEncoder extends MessageToByteEncoder<MyTime> {


//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
//        MyTime m = (MyTime) msg;
//        ByteBuf encoded = ctx.alloc().buffer(4);
//        encoded.writeInt((int) m.value());
//        ctx.write(encoded, promise); // (1)
//    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MyTime msg, ByteBuf out) {
        out.writeInt((int)msg.value());
    }
}