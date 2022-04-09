package com.example.nettyserver.simplecommunication;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月04日 20:57:00
 */
public class ServerTest {
    public static void main(String[] args) {
        //监听端口,接收新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理每一个连接的数据读写线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //引导服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                //给引导类配置线程组
                .group(bossGroup,workerGroup)
                //指定服务端IO模型为NIO
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        //绑定端口
        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功");
                }else {
                    System.out.println("端口绑定失败");
                }
            }
        });
    }
}
