package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.request.LogoutRequestPacket;
import com.cxy.demonetty.procotol.packet.response.LogoutResponsePacket;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登出请求
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    // 2. 构造单例
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    protected LogoutRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
