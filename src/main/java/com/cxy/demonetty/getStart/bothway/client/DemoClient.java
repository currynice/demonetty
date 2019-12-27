package com.cxy.demonetty.getStart.bothway.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class DemoClient {



    /**
     * 网络不稳定，连接失败, 不过连接建立失败不会立即重新连接，而是通过一个指数退避的方式，每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接，
     * 到达一定次数之后就放弃连接，默认最多重试 5 次
     */
        private static void tryConnect(Bootstrap bootstrap,String host,int port,int retry){

            // 4.建立连接,失败重连
            bootstrap.connect(host, port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                } else if (retry == 0) {
                    System.err.println("重试次数已用完，放弃连接！");
                } else {
                    // 第几次重连
                    int order = (5 - retry) + 1;
                    // 本次重连的间隔
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                    if (retry==1){
                        System.err.println("重试次数已用完，放弃连接！");
                    }
                    //获得client的eventLoop完成定时执行
                    bootstrap.config().group().schedule(() -> tryConnect(bootstrap, host, port, retry-1), delay, TimeUnit
                            .SECONDS);
                }
        });
        }


        public static void main(String[] args) {
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .attr(AttributeKey.newInstance("clientName"),"cxy")
                    // 1.指定线程模型
                    .group(workerGroup)
                    // 2.指定 IO 类型为 NIO
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)//表示连接的超时时间
                    .option(ChannelOption.SO_KEEPALIVE, true)//表示是否开启 TCP 底层心跳机制，true 为开启
                    .option(ChannelOption.TCP_NODELAY, true)//是否开始 Nagle 算法，true 表示关闭，false 表示开启
                    // 3.IO 处理逻辑
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            //ch.pipeline()返回当前连接的逻辑处理责任链
                            ch.pipeline().addLast(new DemoClientHandler());
                        }
                    });
            tryConnect(bootstrap,"localhost",8000,5);

        }
    }
