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
 * @createTime 2022年04月04日 21:58:00
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        String s = buf.toString(Charset.forName("utf-8"));
        System.out.println(new Date() + ": 服务端读取数据 -> " + s);

        //服务端返回数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx);

        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "Netty,我是服务端,我已收到你的消息".getBytes(Charset.forName("utf-8"));

        ByteBuf buf = ctx.alloc().buffer();

        buf.writeBytes(bytes);
        return buf;
    }
}
