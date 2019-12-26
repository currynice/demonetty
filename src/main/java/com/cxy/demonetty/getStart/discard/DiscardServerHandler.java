package com.cxy.demonetty.getStart.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 *
 * 参照 io.netty.example.discard，设计一个针对请求没有任何响应的Server
 *  (1) 继承ChannelInboundHandlerAdapter(ChannelInboundHandler的实现类)。
 *  ChannelInboundHandler接口提供了各种可以重写的事件处理程序方法。继承ChannelInboundHandlerAdapter而不是自己实现接口。
 * (2)重写了channelRead()事件处理程序方法。无论何时从客户端接收到新数据，都会使用接收到的消息调用此方法。接收到的消息的类型是ByteBuf。
 * (3)为了实现Discard丢弃协议，处理程序需要忽略接收到的消息。对于ByteBuf对象，必须通过release()方法显式地释放它（基于引用计数算法(Reference_Counted)）。
 *     释放传递给处理程序的任何引用计数对象是处理程序的职责。需要利用try-Catch-finally调用release() 修改引用计数对象
 *    refer to: {@link io.netty.channel.SimpleChannelInboundHandler} 借助模板模式，完成ReferenceCountUtil.release(msg);
 *
 * (4)程序抛出异常时，使用Throwable调用exceptionCaught()事件处理程序方法。最好记录捕获的异常并关闭其相关的通道，关闭之前还可以发送带有errorCode的响应消息。
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)


    /**
     *  运行telnet命令，将看到服务器打印接收到的内容。
     * 丢弃服务器的完整源代码位于发行版的io.netty.example.包中。
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        ByteBuf in = (ByteBuf) msg;
        try {
           System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        } finally {
            //丢掉接收的数据.
            ReferenceCountUtil.release(msg); // (3)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        //异常抛出关闭Server
        cause.printStackTrace();
        ctx.close();
    }

}

