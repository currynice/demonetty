package com.cxy.demonetty.getStart.bothway.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class DemoServer {
    private int port;

    public DemoServer(int port) {
        this.port = port;
    }


    public void start() throws InterruptedException {
        //创建新连接的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        // 连接读取数据，业务处理的线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    //NIO模型
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //encoder在handler之前
                            ch.pipeline().addLast(new DemoServerHandler());
                        }
                    })
                    //系统用于临时存放已完成三次握手的请求的队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 1024)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // 绑定端口并开始接受传入的连接（sync()方法将等待到完成，失败会抛出异常）
              ChannelFuture f = b.bind(port).sync(); // (7)


            //关闭服务器的方法(不用时注释下面的finally块)
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public static void main(String args[]) throws InterruptedException {

        int port = 8000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new DemoServer(port).start();
    }
}
