package com.cxy.demonetty.IM.groupChat.client.console;

import com.cxy.demonetty.procotol.packet.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;


/**
 * 向某个 连接（用户/群）发送消息
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给：");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
