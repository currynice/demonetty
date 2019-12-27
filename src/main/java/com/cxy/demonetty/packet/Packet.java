package com.cxy.demonetty.packet;


import lombok.Data;

/**
 * 数据包对象
 */
@Data
public abstract class Packet {
    //协议版本号
    private Long version;

    //对本包和所有子类可见,子类必须重写
    protected abstract Byte getCommand();
}
