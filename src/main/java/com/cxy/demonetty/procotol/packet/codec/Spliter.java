package com.cxy.demonetty.procotol.packet.codec;

import com.cxy.demonetty.procotol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 自定义拆包器
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    //长度域在数据包的偏移量
    private static final int LENGTH_FIELD_OFFSET = 7;
    //长度域长度
    private static final int LENGTH_FIELD_LENGTH = 4;

    /**
     * 拒绝非本协议连接
     * 只接受以该magicNumber开头的二进制数据
     * 拆完之后，magicNumber 和长度域 都需要丢弃，只剩下版本号和算法，指令，数据
     */
    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }


    /**
     * 通过magicNumber屏蔽非本协议的客户端,不符合自定义协议的，服务端关闭这条连接
     * @param ctx
     * @param in  数据包的开头 目前为止还未拆的数据
     * @return
     * @throws Exception
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
