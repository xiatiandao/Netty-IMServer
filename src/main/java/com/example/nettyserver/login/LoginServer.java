package com.example.nettyserver.login;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author admin
 * @version 1.0.0
 * @Description 登录服务端
 * @createTime 2022年04月08日 14:55:00
 */
public class LoginServer {
    public static void main(String[] args) {
        //接收新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理每一个连接的数据读写线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //引导服务端的启动工作
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap
                //引导类配置线程组
                .group(bossGroup,workerGroup)
                //服务端IO模型为Nio
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler());
                    }
                })
                //绑定监听端口
                .bind(8000);
    }
}
