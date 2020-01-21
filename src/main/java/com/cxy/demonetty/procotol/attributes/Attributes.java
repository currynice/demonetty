package com.cxy.demonetty.procotol.attributes;

import io.netty.util.AttributeKey;

/**
 * 参数
 */
public interface Attributes {
    //登录客户端标志位
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
