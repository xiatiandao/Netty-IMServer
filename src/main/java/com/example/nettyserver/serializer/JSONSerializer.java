package com.example.nettyserver.serializer;

import com.alibaba.fastjson.JSON;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月05日 22:23:00
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
