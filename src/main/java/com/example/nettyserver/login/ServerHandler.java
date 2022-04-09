package com.example.nettyserver.login;

import com.example.nettyserver.serializer.LoginRequestPacket;
import com.example.nettyserver.serializer.Packet;
import com.example.nettyserver.serializer.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author admin
 * @version 1.0.0
 * @Description 服务端逻辑处理器
 * @createTime 2022年04月08日 14:50:00
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        //判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            loginRequestPacket.setVersion(packet.getVersion());
            //登录校验
            if (valid(loginRequestPacket)) {
                //校验成功
                loginRequestPacket.setSuccess(Boolean.TRUE);
                System.out.println("登录成功");
            } else {
                //校验失败
                System.out.println("登录失败");
                loginRequestPacket.setReason("账号密码校验失败");
                loginRequestPacket.setSuccess(Boolean.FALSE);
            }

            //编码
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx, loginRequestPacket);

            ctx.channel().writeAndFlush(responseByteBuf);

        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
