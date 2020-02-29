package com.cxy.demonetty.IM.groupChat.server;

import com.cxy.demonetty.procotol.packet.codec.PacketCodecHandler;
import com.cxy.demonetty.procotol.packet.codec.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;


public class GroupChatServer {
    private static final int PORT = 8000;

    public static void main(String args[]){
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        //创建新连接的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();

        //读取数据，业务处理的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //为引导类配置线程组
        serverBootstrap
                .group(boss,worker)
                .channel(NioServerSocketChannel .class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        //插入到最后面的话，如果这条连接读到了数据，但是在 inBound 传播的过程中出错了
                        // 或者数据处理完完毕就不往后传递了，那么最终 IMIdleStateHandler 就不会读到数据，最终导致误判
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        //使用单例模式，避免了创建很多小的对象。
                        // handler 如果有与 channel 相关成员变量，绑定在 channel 的属性上，尽量写成单例的。
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        //无需登陆
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
//                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(LogoutRequestHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                            }
                        });
        bind(serverBootstrap, PORT);

    }



    private static void bind(final ServerBootstrap serverBootstrap, final int port) {

        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {

                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");

            } else {

                System.err.println("端口[" + port + "]绑定失败!");

            }

        });

    }

}
