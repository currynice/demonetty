package com.cxy.demonetty.procotol.serializer;

/**
 * 自定义序列化接口
 */
public interface IMSerializer {
    //默认Jackson序列化json
    byte JSON_SERIALIZER = 1;

    IMSerializer DEFAULT = new JSONSerializer();

    /**
     * 传输的序列化算法 标识
     */
    byte getSerializerAlogrithm();

    /**
     * 编码：java 对象转换成二进制
     */
    byte[] serialize(Object object) ;

    /**
     * 解码：二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
