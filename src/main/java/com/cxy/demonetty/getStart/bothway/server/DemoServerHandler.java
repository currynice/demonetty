package com.cxy.demonetty.getStart.bothway.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class DemoServerHandler extends ChannelInboundHandlerAdapter {

    //接收到客户端发来的数据之后被回调
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        final ByteBuf hellobuf =(ByteBuf)msg;

        System.out.println("from client:"+ hellobuf.toString(StandardCharsets.UTF_8));

        //回复client
        System.out.println(new Date() + ": server写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好鸭!".getBytes(StandardCharsets.UTF_8);

        ByteBuf buffer = ctx.alloc().buffer(8);

        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
