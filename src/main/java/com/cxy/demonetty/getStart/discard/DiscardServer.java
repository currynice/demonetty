package com.cxy.demonetty.getStart.discard;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 使用{@link DiscardServerHandler}  启动一个 Discard 服务器
 * (1)NioEventLoopGroup是一个处理I/O操作的多线程事件循环。Netty为不同类型的传输提供了各种EventLoopGroup实现。
 *    第一个通常接受传入连接。第二个连接通在boss接受连接并将接受的连接注册到worker之后，它处理接受连接的流量。
 *       具体使用多少线程以及如何将它们映射到创建的通道取决于EventLoopGroup内部实现。
 *
 * (2)ServerBootstrap引导类，用于设置服务器。指定使用NioServerSocketChannel类，该类用于实例化一个新通道来接受传入的连接。
 *     ChannelInitializer是用于帮助用户配置新通道的特殊处理程序。添加了一个处理程序(DiscardServerHandler)来配置新通道的ChannelPipeline。
 * (3)指定NioServerSocketChannel作为服务端Channel接受新连接
 *
 * (4) ChannelInitializer用于帮助用户配置新通道的特殊处理程序。
 * (5) (6)为TCP连接设置了一些参数
 * (7)绑定本地端口8080,直到成功。
 *
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
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
                            ch.pipeline().addLast(new DiscardServerHandler());
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
        new DiscardServer(port).start();
    }
}
