package com.cxy.demonetty.IM.groupChat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 15;

    /**
     * readerIdleTime:读空闲时间（这段时间内没有数据读到，连接假死）
     * writerIdleTime:写空闲时间（这段时间内没有写数据，连接假死）,0表示不关心
     * allIdleTime:读写空闲时间（这段时间内没有读写数据，连接假死）,0表示不关心
     * unit:时间单位->15s
     */
    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
