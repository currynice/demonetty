package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.request.JoinGroupRequestPacket;
import com.cxy.demonetty.procotol.packet.response.JoinGroupResponsePacket;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    // 2. 构造单例
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    protected JoinGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();

        //todo 默认成功
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
