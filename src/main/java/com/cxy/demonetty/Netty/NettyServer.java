package com.cxy.demonetty.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {



    public static void main(String args[]){
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //创建新连接的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();

        //读取数据，业务处理的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //为引导类配置线程组
        serverBootstrap
                .group(boss,worker)
                //指定NIO模型(BIO:OioServerSocketChannel.class)
                .channel(NioServerSocketChannel.class)
                //定义连接读取数据，业务处理的逻辑(NioSocketChannel:Netty对NIO类型的连接Channel抽象)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        //SimpleChannelInboundHandler的泛型参数表示只接收这种类型的数据,msg的release()包含在模板方法中了，不需要
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            //telnet localhost [port]  进行验证
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println("server打印:"+msg);
                            }
                        });
                    }
                });

        doBind(serverBootstrap,8000);
    }

    /**
     * 测试{@link ServerBootstrap#handler(ChannelHandler)} 处理服务端启动的一些逻辑
     */
    private static void testHandler(ServerBootstrap serverBootstrap){
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch){
                System.out.println("服务端启动中");
            }
        });
    }


    /**
     * 从 initPort 端口号，开始往上找端口号，直到端口绑定成功
     * @param serverBootstrap
     * @param initPort
     */
    private static void doBind(ServerBootstrap serverBootstrap,final int initPort ){
        //绑定本地initPort端口,异步方法，返回ChannelFuture
        serverBootstrap.bind(initPort)
       // 为上面的绑定增加一个监听器，以判断端口是否绑定成功
                .addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + initPort + "]绑定成功!");
                } else {
                    System.err.println("端口[" + initPort + "]绑定失败!");
                    doBind(serverBootstrap, initPort + 1);
                }
            }
        });
    }

    /**
     * 给服务端的 channel---NioServerSocketChannel指定一些自定义属性(维护一个Map)，通过channel.attr()取出这个属性，
     * 那么，当然，除了可以给服务端 channel NioServerSocketChannel指定一些自定义属性之外，我们还可以给每一条连接指定自定义属性
     */
    private static void testAttr(ServerBootstrap serverBootstrap){
        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");
    }

    /**
     * 给每一条连接指定自定义属性,通过channel.attr()取出这个属性
     */
    private static void testChildAttr(ServerBootstrap serverBootstrap){
        serverBootstrap.childAttr(AttributeKey.newInstance("childKey"), "childValue");
    }

    /**
     * 给服务端Channle设置一些TCP相关的属性
     * SO_BACKLOG: 系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
     */
    private static void testOption(ServerBootstrap serverBootstrap){
        serverBootstrap.option(ChannelOption.SO_BACKLOG,1024);
    }

    /**
     * 给每条连接设置一些TCP相关的属性
     *
     * ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
     * ChannelOption.TCP_NODELAY表示是否开启Nagle算法:
     *      true(关闭:要求高实时性，有数据发送时就马上发送)
     *      false(开启: 需要减少发送次数减少网络交互)
     */
    private static void testChildOption(ServerBootstrap serverBootstrap){
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY,Boolean.TRUE);
    }
}
