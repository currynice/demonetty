package com.cxy.demonetty.IM.groupChat.client.console;

import com.cxy.demonetty.procotol.packet.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 创建群聊
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 组装 创建群聊请求数据包
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.print("【拉人群聊】请输入 userId 列表，以英文逗号隔开：");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
