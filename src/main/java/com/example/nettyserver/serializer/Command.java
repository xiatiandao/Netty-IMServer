package com.example.nettyserver.serializer;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月05日 18:31:00
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
