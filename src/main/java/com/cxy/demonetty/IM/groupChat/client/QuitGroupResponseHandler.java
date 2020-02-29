package com.cxy.demonetty.IM.groupChat.client;

import com.cxy.demonetty.procotol.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;



public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket responsePacket) {
        if (responsePacket.isSuccess()) {
            System.out.println("退出群聊[" + responsePacket.getGroupId() + "]成功！");
        } else {
            System.out.println("退出群聊[" + responsePacket.getGroupId() + "]失败！");
        }

    }
}
