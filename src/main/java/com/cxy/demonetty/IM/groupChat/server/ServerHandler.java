package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.Packet;
import com.cxy.demonetty.procotol.packet.PacketCodeC;
import com.cxy.demonetty.procotol.packet.request.LoginRequestPacket;
import com.cxy.demonetty.procotol.packet.request.MessageRequestPacket;
import com.cxy.demonetty.procotol.packet.response.LoginResponsePacket;
import com.cxy.demonetty.procotol.packet.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 服务端处理登录请求 todo 处理消息分支改进
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE().decode(requestByteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ": 收到来自客户端的登录请求……");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(loginRequestPacket.getVersion());
            // 登录校验
            if (valid(loginRequestPacket)) {
                responsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功!");
            } else {
                responsePacket.setReason("账号密码校验失败");
                responsePacket.setSuccess(false);
                System.out.println(new Date() + ": 登录失败!");
            }
            // 编码
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE().encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }else if (packet instanceof MessageRequestPacket) {
            // 处理消息
            MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) packet);
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE().encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
