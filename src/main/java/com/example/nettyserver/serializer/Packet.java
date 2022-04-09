package com.example.nettyserver.serializer;

import lombok.Data;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月05日 18:26:00
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
