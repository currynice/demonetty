package com.cxy.demonetty.IM.groupChat.client;

import com.cxy.demonetty.procotol.packet.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        //ctx.executor()得到当前channel的NIO线程，线程执行延时间任务，通过递归每隔5s发送一个心跳包,
        // 时间段通常要比服务端的空闲检测时间的一半要短一些, 为了排除公网偶发的秒级抖动。
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }

        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
