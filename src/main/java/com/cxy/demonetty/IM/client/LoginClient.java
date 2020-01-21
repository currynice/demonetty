package com.cxy.demonetty.IM.client;

import com.cxy.demonetty.procotol.packet.MessageRequestPacket;
import com.cxy.demonetty.procotol.packet.PacketCodeC;
//import com.cxy.demonetty.procotol.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class LoginClient {

    private static final int MAX_RETRY = 5;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8000;

    public static void main(String[] args) {



        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new ClientLoginHandler());
                    }
                });

        //连接逻辑
        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }

    /**
     * 网络不稳定，连接失败, 不过连接建立失败不会立即重新连接，而是通过一个指数退避的方式，每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接，
     * 到达一定次数之后就放弃连接，默认最多重试 5 次
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {

        bootstrap.connect(host, port).addListener(future -> {

            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功!");

                Channel clientChannel = ((ChannelFuture)future).channel();
                startConsoleThread(clientChannel);

            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {

                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔

                int delay = 1 << order;

                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");

                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit

                        .SECONDS);
            }
        });
    }


    private static void startConsoleThread(Channel channel){
        new Thread(()->{
            while (!Thread.interrupted()) {
//                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入发送至服务端的消息: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketCodeC.INSTANCE().encode(channel.alloc(), packet);
                    channel.writeAndFlush(byteBuf);
//                }
            }
        }).start();
    }
}
