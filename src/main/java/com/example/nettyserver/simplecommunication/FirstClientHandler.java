package com.example.nettyserver.simplecommunication;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月04日 21:46:00
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据");

        //获取数据
        ByteBuf buf = getByteBuf(ctx);

        // 写数据
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读取数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象ButeBuf
        ByteBuf buffer = ctx.alloc().buffer();

        // 2. 准备数据,指定字符串的字符集
        byte[] bytes = "你好,Netty !".getBytes(Charset.forName("utf-8"));

        //填充数据到buf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
