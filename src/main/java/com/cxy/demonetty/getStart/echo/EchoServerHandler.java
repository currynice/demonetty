package com.cxy.demonetty.getStart.echo;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *  DiscardServer 使用数据而没有响应。服务器通常是用来响应请求的。ECHO协议: 服务器对于任何接收到的数据都发回。
 *  而不是将接收到的数据打印到控制台。
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ChannelHandlerContext对象提供各种操作，触发各种I/O事件和操作。
     * (1): write():逐字地编写接收到的消息。当它被写出来的时候，Netty会为release()。
     * (2): write() 消息没有立即被写入线路。它在内部进行缓冲，通过ctx.flush()释放到连接上。
     * ctx.writeAndFlush(msg)是一个结合操作.
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

            ctx.write(msg); // (1)
            ctx.flush(); // (2)

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        //异常抛出关闭Server
        cause.printStackTrace();
        ctx.close();
    }


}

