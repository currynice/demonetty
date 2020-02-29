package com.cxy.demonetty.procotol.packet;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据包对象
 */
@Data
public abstract class Packet implements Serializable {
    /**
     * 协议版本号,默认1 ,以1个字节表示
     */
    @JsonIgnore
    private Byte version = 1;

    /**
     * 返回指令对本包和所有子类可见,子类必须重写
     * 方法名不以get开头了,不然会报UnrecognizedPropertyException错误
     */
    @JsonIgnore
    public abstract Byte packetCommand();
}
