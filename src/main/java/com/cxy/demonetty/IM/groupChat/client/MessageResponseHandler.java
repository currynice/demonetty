package com.cxy.demonetty.IM.groupChat.client;

import com.cxy.demonetty.procotol.packet.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 客户端收消息逻辑
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {


    /**
     * 客户端处理登录响应
     * @param ctx
     * @param messageResponsePacket
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(new Date() + fromUserId + ":" + fromUserName + " -> " + messageResponsePacket .getMessage());
    }
}
