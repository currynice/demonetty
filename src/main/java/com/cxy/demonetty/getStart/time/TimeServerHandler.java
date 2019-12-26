package com.cxy.demonetty.getStart.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * https://baike.baidu.com/item/%E6%97%B6%E9%97%B4%E5%8D%8F%E8%AE%AE/11049016?fr=aladdin
 * time协议:对客户端发送一个包含32位整数的消息,被网络时间协议替代
 *
 * 不接收任何请求即忽略任何接收到的数据，在连接建立后立即发送消息，不使用channelRead()。而是重写channelActive()
 *
 * 发送消息后关闭连接。
 *
 *
 * (1)建立连接并准备生成流量时，将调用channelActive()方法。
 * (2)要发送新消息，先分配一个新的缓冲区来包含消息。写数据是一个32位的整数，因此需要一个容量至少为4字节的ByteBuf。
 *            通过ChannelHandlerContext.alloc()获取当前的ByteBufAllocator,从中分配一个新的缓冲区。
 *
 *
 * java.nio.ByteBuffer.flip()反转指针，开始读取Buffer中的数据, ByteBuf不太一样，它有两个指针;一个用于读操作readIndex，另一个用于写操作writeIndex。
 *          向ByteBuf写入内容时，writer索引会向后移动，而reader索引不变。reader索引和writer索引分别表示消息开始和结束的位置。
 *          改进了NIO Buffer没有提供一种明确的方法来确定消息内容的起始和结束位置,导致必须要调用flip方法, 当忘记翻转缓冲区时，可能不会发送任何内容或发送了不正确的数据。
 *     所以Netty改进了这一点
 *
 * (3)ChannelHandlerContext.write()和 writeAndFlush()方法返回一个ChannelFuture。
 *          ChannelFuture意味着，可能还没有执行任何请求的操作，因为操作都是异步的。
 *
 *    为了保证 ChannelFuture完成后再调用close()方法，为其添加一个监听，write()完成后通知监听器。close()也可能不会立即关闭连接，返回的也是ChannelFuture。
 *
 * (4)创建了一个新的匿名ChannelFutureListener，在操作完成时关闭Channel。
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    //unix下可以使用rdate进行测试
}
