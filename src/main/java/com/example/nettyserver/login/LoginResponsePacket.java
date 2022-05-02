package com.example.nettyserver.login;

import com.example.nettyserver.serializer.Packet;
import lombok.Data;

import static com.example.nettyserver.serializer.Command.MESSAGE_RESPONSE;

/**
 * @author qijs
 * @description
 * @date 2022/5/1
 */
@Data
public class LoginResponsePacket extends Packet {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 成功状态
     */
    private Boolean success;

    /**
     * 原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
