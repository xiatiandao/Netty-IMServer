package com.example.nettyserver.login;

import com.example.nettyserver.serializer.Packet;
import lombok.Data;

import static com.example.nettyserver.serializer.Command.LOGIN_REQUEST;

/**
 * @author admin
 * @version 1.0.0
 * @Description 登录请求数据包
 * @createTime 2022年04月05日 18:28:00
 */
@Data
public class LoginRequestPacket extends Packet {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

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
        return LOGIN_REQUEST;
    }
}
