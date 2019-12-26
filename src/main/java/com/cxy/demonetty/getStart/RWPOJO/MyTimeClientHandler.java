package com.cxy.demonetty.getStart.RWPOJO;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;



/**
 * (1):有了MyTimeDecoder，MyTimeClientHandler不再使用ByteBuf,也没有ByteBuf需要去release了
 *
 *     同样的技术也可以应用于服务器端
 */
public class MyTimeClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf m = (ByteBuf) msg;
         MyTime m = (MyTime)msg;
         System.out.println(m);
         ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
