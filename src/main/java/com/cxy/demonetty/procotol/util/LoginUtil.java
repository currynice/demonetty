package com.cxy.demonetty.procotol.util;


import com.cxy.demonetty.procotol.attributes.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;


//https://github.com/lightningMan/flash-netty/tree/实现客户端与服务端收发消息
public class LoginUtil {

    /**
     * 给连接Channel绑定属性
     * @param channel
     */
    public static void markAsLogin(Channel channel){

        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否登录
     * @param channel
     */
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return  loginAttr.get()!=null;

    }
}
