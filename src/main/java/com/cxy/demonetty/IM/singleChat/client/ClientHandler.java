package com.cxy.demonetty.IM.singleChat.client;

import com.cxy.demonetty.procotol.packet.Packet;
import com.cxy.demonetty.procotol.packet.response.LoginResponsePacket;
import com.cxy.demonetty.procotol.packet.response.MessageResponsePacket;
import com.cxy.demonetty.procotol.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 客户端登录逻辑
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
//    /**
//     * 注释：改为手动输入密码进行登陆
//     客户端发送登录请求
//     * @param ctx
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": 客户端开始登录");
//
//        // 创建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUserName("cxy");
//        loginRequestPacket.setPassword("123");
//
//        // ctx.alloc()获取与当前连接相关的ByteBuf分配器
//        ByteBuf buffer = PacketCodeC.INSTANCE().encode(ctx.alloc(), loginRequestPacket);
//
//        // 写数据
//        ctx.channel().writeAndFlush(buffer);
//    }

    /**
     * 客户端处理登录响应
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE().decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket)packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());

        }
    }
}
