import com.cxy.demonetty.procotol.packet.LoginRequestPacket;
import com.cxy.demonetty.procotol.packet.Packet;
import com.cxy.demonetty.procotol.packet.PacketCodeC;
import com.cxy.demonetty.procotol.serializer.IMSerializer;
import com.cxy.demonetty.procotol.serializer.JSONSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class Test1 {

    @Test
    public void testCodeC() throws JsonProcessingException {

        IMSerializer serializer = new JSONSerializer();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();



        loginRequestPacket.setVersion(((byte) 1));

        loginRequestPacket.setUserId(UUID.randomUUID().toString());

        loginRequestPacket.setUsername("zhangsan");

        loginRequestPacket.setPassword("password");



        PacketCodeC packetCodeC = PacketCodeC.INSTANCE();

        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT, loginRequestPacket);

        Packet decodedPacket = packetCodeC.decode(byteBuf);



        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));



    }
}
