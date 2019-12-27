package com.cxy.demonetty.getStart.RWPOJO;



import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端将32位二进制数据转换为日期。
 * Netty中，服务器和客户机之间最大且惟一的区别是使用不同的引导程序和通道实现。
 */
public class MyTimeClient {


    /**
     * (1): Bootstrap与ServerBootstrap类似，用于非服务器通道[客户端通道或无连接通道]。
     * (2): 如果您只指定一个EventLoopGroup，那么它将同时用作boss组和worker组。但是，boss worker并不用于客户端。
     * (3): NioSocketChannel用来创建客户端通道，而不是NioSocketChannel。
     * (4): Bootstrap没有childOption()方法，因为客户端SocketChannel没有父通道。
     * (5): 调用connect()方法打开客户端，不是bind()方法。
     *
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);

        String host = "localhost";
        int port = 8000;
        EventLoopGroup workerGroup = new NioEventLoopGroup();


            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MyTimeDecoder(),new MyTimeClientHandler());
                }
            });

            // Start the client.
             b.connect(host, port).addListener(future -> {
                if(future.isSuccess()){
                    System.out.println("client connect success !");
                }else {
                    System.out.println("client connect fail !");
                }
             });

    }
}
