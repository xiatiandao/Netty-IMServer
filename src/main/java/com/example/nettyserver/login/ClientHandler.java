package com.example.nettyserver.login;

import com.example.nettyserver.sendmessage.MessageResponsePacket;
import com.example.nettyserver.serializer.Packet;
import com.example.nettyserver.serializer.PacketCodeC;
import com.example.nettyserver.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @author admin
 * @version 1.0.0
 * @Description 客户端逻辑处理器
 * @createTime 2022年04月08日 14:50:00
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端开始登录");

        //创建登录对象
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUserName("张三老师");
        requestPacket.setPassword("123");

        //编码
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.channel().alloc().ioBuffer(),requestPacket);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端接收服务端返回消息");
        ByteBuf byteBuf = (ByteBuf) msg;

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginResponsePacket = (LoginRequestPacket) packet;

            if (loginResponsePacket.getSuccess().equals(Boolean.TRUE)) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
            }else {
                System.out.println(new Date() + ": 客户端登录失败,失败原因 : " +
                        loginResponsePacket.getReason());
            }
        }else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + " : 收到服务端的消息 :" + messageResponsePacket.getMessage());
        }
    }
}
