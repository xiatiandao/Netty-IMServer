package com.example.nettyserver.simplecommunication;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月04日 21:21:00
 */
public class ClientTest {
    public static void main(String[] args) {
        //线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //指定线程模型
                .group(workerGroup)
                //指定IO类型为Nio
                .channel(NioSocketChannel.class)
                //IO处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        //建立连接
        bootstrap.connect("127.0.0.1",8000)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    }else {
                        System.out.println("连接失败");
                    }
                });
    }
}
