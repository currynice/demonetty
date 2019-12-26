package com.cxy.demonetty.getStart.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 这将使解码器更加简化。不过，您需要查阅API参考以获得更多信息
 *
 * Netty提供了:
 * 对于二进制协议位于 io.netty.example.factorial
 * 对于基于文本行的协议位于 io.netty.example.telnet
 */
public class OriginalTimeDecoder extends ReplayingDecoder<Void> {
        @Override
        protected void decode(
                ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            out.add(in.readBytes(4));
        }
    }
