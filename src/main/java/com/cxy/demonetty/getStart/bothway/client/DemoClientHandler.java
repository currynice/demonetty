package com.cxy.demonetty.getStart.bothway.client;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;


public class DemoClientHandler extends ChannelInboundHandlerAdapter {

   //client与server建立连接后被调用,逻辑:client向server写一句话
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": client写出数据");

        // 1. 准备数据
        ByteBuf buf = getByteBuf(ctx);

        // 2. 写buf数据
        ctx.channel().writeAndFlush(buf);
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buf = ctx.alloc().buffer(6);

        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好".getBytes(Charset.forName("utf-8"));
        // 3. 填充数据到 ByteBuf
        return buf.writeBytes(bytes);
    }

    //client收到server的数据时触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": from server -> " + byteBuf.toString(Charset.forName("utf-8")));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
