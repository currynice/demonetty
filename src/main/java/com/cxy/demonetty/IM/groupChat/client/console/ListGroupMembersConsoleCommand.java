package com.cxy.demonetty.IM.groupChat.client.console;

import com.cxy.demonetty.procotol.packet.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();

        System.out.print("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
