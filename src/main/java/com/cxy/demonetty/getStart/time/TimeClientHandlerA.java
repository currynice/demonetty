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
 *   32位整数是非常小的数据量，不太可能经常被分割。然而，它可能是碎片化的，并且碎片化的可能性会随着流量的增加而增加。
 *
 *   解决方案A:
 *   创建一个内部累积缓冲区buf，并等待所有4个字节都被接收到内部缓冲区。
 *   ChannelHandler有两个生命周期侦听器方法:handlerAdded()和handlerremove()。只要不阻塞很长时间，就可以执行一些初始化任务。
 * (2)把所有接收到的数据都应该累积到buf中。
 * (3)处理程序先检查buf是否有足够的数据(4字节)，再处理实际的业务逻辑。当更多的数据到达时，Netty将再次调用channelRead()方法，并最终累积所有4个字节。
 *
 *
 */
public class TimeClientHandlerA extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    //添加hanler时
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(4);
    }

    //移除hanler时
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        buf.writeBytes(m);//（2）
        m.release();

       if(buf.readableBytes()>=4) {// (3)
           long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
           System.out.println(new Date(currentTimeMillis));
           ctx.close();
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
        cause.printStackTrace();// (3)
        ctx.close();
    }
}
