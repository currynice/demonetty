package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.request.CreateGroupRequestPacket;
import com.cxy.demonetty.procotol.packet.response.CreateGroupResponsePacket;
import com.cxy.demonetty.procotol.util.IDUtil;
import com.cxy.demonetty.procotol.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    // 2. 构造单例
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    protected CreateGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();
        // 1. 创建一个 channel 分组把多个 chanel 的操作聚合在一起，
        // 可以往里面添加,删除 channel，进行 channel 的批量读写，关闭等操作，
        // ChannelGroup其实就是 一些channel 的分组集合
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }else {
                System.out.println(userId+"不存在或掉线");
            }
        }

        // 3. 创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IDUtil.randomId());
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4. 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

        // // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(createGroupResponsePacket.getGroupId(),channelGroup);

    }
}
