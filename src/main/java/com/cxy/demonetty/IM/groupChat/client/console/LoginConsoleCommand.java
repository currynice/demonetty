package com.cxy.demonetty.IM.groupChat.client.console;

import com.cxy.demonetty.procotol.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("请登陆: ");
        System.out.print("请输入姓名: ");
        String username = scanner.nextLine();
        loginRequestPacket.setUserName(username);

        //密码使用默认的
        System.out.print("密码使用默认的");
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
