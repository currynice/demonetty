package com.cxy.demonetty.procotol.serializer;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONSerializer implements IMSerializer{
    @Override
    public byte getSerializerAlogrithm() {

    return SerializerAlgorithm.JSON;
}

        @Override
        public byte[] serialize(Object object)  {

            ObjectMapper mapper = new ObjectMapper();
            //有歧义的方法名已做处理
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                return mapper.writeValueAsBytes(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public <T> T deserialize(Class<T> clazz, byte[] bytes)  {
            ObjectMapper mapper = new ObjectMapper();
            //有歧义的方法名已做处理
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                return mapper.readValue(bytes,clazz);
            } catch (IOException e) {
                e.printStackTrace();
               return null;
            }
        }

}
