package com.cxy.demonetty.getStart.RWPOJO;


import java.util.Date;

/**
 *  除了ByteBuf可以作为协议消息的主要数据结构。也可以使用POJO而不是ByteBuf。
 * 在ChannelHandlers中使用POJO的优势是显而易见的:
 *  通过将从ByteBuf中提取信息的代码从处理程序中分离出来，程序变得更加可维护和可重用。在实际中很有用处
 */
public class MyTime {
    private final long value;

    public MyTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public MyTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}
