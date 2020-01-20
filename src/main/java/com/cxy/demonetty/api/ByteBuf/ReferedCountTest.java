package com.cxy.demonetty.api.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 使用到 slice 和 duplicate 方法的时候，千万要理清内存共享，引用计数共享
 */
public class ReferedCountTest {

    /**
     * 不释放造成内存泄漏
     */
    public static void NoRelease(){

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        //do something
        doWith(buffer);
        //此时buffer引用计数为2,release后变为1，存在内存泄露的风险
        buffer.release();
    }

    private static void doWith(ByteBuf buffer){
        //
        //增加引用计数
        ByteBuf slice = buffer.retainedSlice();

    }

    public static void main(String args[]){

        NoRelease();
    }



}
