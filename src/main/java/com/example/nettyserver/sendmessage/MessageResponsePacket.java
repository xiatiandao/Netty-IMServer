package com.example.nettyserver.sendmessage;

import com.example.nettyserver.serializer.Packet;
import lombok.Data;

import static com.example.nettyserver.serializer.Command.MESSAGE_RESPONSE;

/**
 * @author qijs
 * @description
 * @date 2022/5/1
 */
@Data
public class MessageResponsePacket extends Packet {
    /**
     * 消息体
     */
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
