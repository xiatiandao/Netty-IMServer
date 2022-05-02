package com.example.nettyserver.login;

import com.example.nettyserver.sendmessage.MessageRequestPacket;
import com.example.nettyserver.serializer.PacketCodeC;
import com.example.nettyserver.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author admin
 * @version 1.0.0
 * @Description 登录客户端
 * @createTime 2022年04月08日 14:55:00
 */
public class LoginClient {
    public static void main(String[] args) {
        //
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });

        connect(bootstrap,"127.0.0.1",8000,null);
    }

    private static void connect(Bootstrap bootstrap,String host,Integer port,Integer retry) {
        bootstrap.connect(host,port).addListener(future -> {
            System.out.println("future状态 :" + future.isSuccess());
            if (future.isSuccess()) {
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("channel状态 :" + channel);
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端 : ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);

                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().ioBuffer(), packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
