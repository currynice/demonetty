package com.cxy.demonetty.IM.groupChat.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台动作抽象接口
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
