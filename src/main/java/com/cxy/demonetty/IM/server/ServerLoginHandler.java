package com.cxy.demonetty.IM.server;

import com.cxy.demonetty.procotol.packet.LoginRequestPacket;
import com.cxy.demonetty.procotol.packet.LoginResponsePacket;
import com.cxy.demonetty.procotol.packet.Packet;
import com.cxy.demonetty.procotol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerLoginHandler extends ChannelInboundHandlerAdapter {

    /**
     * 服务端处理登录请求
     * @param ctx
     * @param msg
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE().decode(requestByteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(loginRequestPacket.getVersion());
            // 登录校验
            if (valid(loginRequestPacket)) {
                responsePacket.setSuccess(true);
            } else {
                responsePacket.setReason("账号密码校验失败");
                responsePacket.setSuccess(false);
            }
            // 编码
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE().encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return false;
    }
}
