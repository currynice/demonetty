package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.request.QuitGroupRequestPacket;
import com.cxy.demonetty.procotol.packet.response.QuitGroupResponsePacket;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 退出群聊
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    // 2. 构造单例
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    protected QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        // 2. 构造 退群 响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        //todo 默认成功
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
