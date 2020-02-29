package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.cxy.demonetty.procotol.packet.Command.*;

/**
 * 缩短事件传播路径 压缩为一个 handler,合并平行关系的handler
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    /**
     * 通过指令找到具体的 handler
     * @param ctx
     * @param packet
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.packetCommand()).channelRead(ctx, packet);
    }
}
