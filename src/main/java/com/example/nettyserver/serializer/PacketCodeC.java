package com.example.nettyserver.serializer;

import com.example.nettyserver.login.LoginRequestPacket;
import com.example.nettyserver.login.LoginResponsePacket;
import com.example.nettyserver.sendmessage.MessageRequestPacket;
import com.example.nettyserver.sendmessage.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

import static com.example.nettyserver.serializer.Command.*;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月05日 22:40:00
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0X12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte,Class<? extends Packet>> packetTypeMap;
    private final Map<Byte,Serializer> serializerMap;

    public PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        JSONSerializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    /**
     * 编码
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf,Packet packet) {

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        //版本号
        byteBuf.writeByte(packet.getVersion());
        //序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        //指令
        byteBuf.writeByte(packet.getCommand());
        //数据长度
        byteBuf.writeInt(bytes.length);
        //数据
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔数
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标识
        byte serializerAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();
        //数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null){
            return serializer.deserialize(requestType,bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
