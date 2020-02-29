package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.IM.session.Session;
import com.cxy.demonetty.procotol.packet.request.MessageRequestPacket;
import com.cxy.demonetty.procotol.packet.response.MessageResponsePacket;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    // 2. 构造单例
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    protected MessageRequestHandler() {
    }
    /**
     * 服务端处理登录请求
     * @param ctx
     * @param messageRequestPacket
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {


        // 只能处理消息请求数据包
//            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());


                MessageResponsePacket messageResponsePacket = receiveMessage(messageRequestPacket,ctx.channel());
//            ByteBuf responseByteBuf = PacketCodeC.INSTANCE().encode(ctx.alloc(), messageResponsePacket);

        // 3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4.将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }
                // server相当于转发消息至目标channel 注释 ctx.channel().writeAndFlush(messageResponsePacket);
    }


    private MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket, Channel channel){
        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(channel);
        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        return messageResponsePacket;
    }
}
