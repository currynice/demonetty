package com.cxy.demonetty.IM.groupChat.client.console;

import com.cxy.demonetty.procotol.packet.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 加入群聊
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 组装 加入群聊请求数据包
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        System.out.print("【输入groupId加入群聊】");
        String groupId = scanner.next();
        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
