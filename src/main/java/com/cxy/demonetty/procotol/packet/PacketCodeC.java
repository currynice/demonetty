package com.cxy.demonetty.procotol.packet;

import com.cxy.demonetty.procotol.serializer.IMSerializer;
import com.cxy.demonetty.procotol.serializer.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


import java.util.HashMap;
import java.util.Map;

import static com.cxy.demonetty.procotol.packet.Command.*;


public class PacketCodeC {

    private final static PacketCodeC singlePacketCodeC = new PacketCodeC();

    public static PacketCodeC INSTANCE(){
        return singlePacketCodeC;
    }



    private static final int MAGIC_NUMBER = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    private final Map<Byte, IMSerializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();

        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);

        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);


        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);

        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();

        IMSerializer serializer = new JSONSerializer();

        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    /**
     * 编码：对象封装为数据包
     */
    public ByteBuf encode(ByteBufAllocator allocator,Packet packet) {
        // 1. 创建适合IO的ByteBuf 对象(DirectByteBuffer直接内存即堆外内存，不受JVM堆管理)
        ByteBuf byteBuf = allocator.ioBuffer();
        // 2. 序列化 Java 对象
        IMSerializer jsonSerializer = IMSerializer.DEFAULT;
        byte[] bytes = jsonSerializer.serialize(packet);


        // 3. 实际编码过程
        //魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        //版本号
        byteBuf.writeByte(packet.getVersion());
        //序列化算法标识
        byteBuf.writeByte(jsonSerializer.getSerializerAlogrithm());
        //指令
        byteBuf.writeByte(packet.packetCommand());
        //数据长度 todo 长度限制
        byteBuf.writeInt(bytes.length);
        //数据
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 解码：数据包解析成对象
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过(读指针右移) magic number
        byteBuf.skipBytes(4);

        // 跳过协议版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        //根据数据包长度取出字节数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        //通过指令拿到该数据包对应的 Java 对象的类型
        Class<? extends Packet> requestType = getRequestType(command);
        //根据序列化算法标识拿到序列化对象
        IMSerializer serializer = getSerializer(serializeAlgorithm);

        //将字节数组转换为 Java 对象
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }


    /**
     * 根据序列化算法标识返回序列化器
     * @param serializeAlgorithm
     * @return
     */
    private IMSerializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);

    }


    /**
     * 根据指令标识返回对应的类
     * @param command
     * @return
     */
    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
