package com.cxy.demonetty.IM.singleChat.server;

import com.cxy.demonetty.procotol.packet.codec.PacketDecoder;
import com.cxy.demonetty.procotol.packet.codec.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;


public class LoginServer {
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
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        //登陆认证handler
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
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
