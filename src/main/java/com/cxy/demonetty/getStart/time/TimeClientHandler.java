package com.cxy.demonetty.getStart.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;


/**
 * 客户端ChannelHandler实现:
 *   从服务器接收一个32位整数，将其转换为时间，打印并关闭连接
 *
 *  (1)把服务端发送的数据读入ByteBuf。
 *  (2)这个处理程序有时会拒绝引发IndexOutOfBoundsException。
 *
 *
 *
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        } finally {
            m.release();
        }
    }

    /**
     * TCP中，接收的数据存储在套接字接收缓冲区中。基于流的传输的缓冲区不是数据包队列，而是字节队列。
     * 这意味着，两条消息作为两个独立的数据包发送，操作系统不把它们视为两条消息，而只是一堆字节。
     * 因此，不能保证所读的内容与远程对等端所写的内容完全相同。
     *
     * 因此，接收部分，不管是用服务器端还是客户端接，都应该将接收到的数据整理成一个或多个有意义的帧，这些帧很容易被应用程序逻辑理解。在上面的例子中，接收到的数据应该被框起来如下:
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
