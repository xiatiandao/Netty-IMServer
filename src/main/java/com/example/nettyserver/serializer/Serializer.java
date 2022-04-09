package com.example.nettyserver.serializer;

/**
 * @author admin
 * @version 1.0.0
 * @Description 序列化接口
 * @createTime 2022年04月05日 18:32:00
 */
public interface Serializer {

    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();
    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * Java 对象转换成二进制对象
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制数据转换成Java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
