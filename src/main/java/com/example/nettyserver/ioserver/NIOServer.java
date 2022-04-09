package com.example.nettyserver.ioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @Description Java原生NIO实现客户端服务端通信
 * @createTime 2022年04月03日 14:33:00
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建通道管理器
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(()->{
            //对应IO编程中服务端启动
            try {
                //创建通道
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                //将ServerSocketChannel对应的serverSocket绑定到指定端口
                listenerChannel.socket().bind(new InetSocketAddress(8000));
                //将通道设置为非阻塞
                listenerChannel.configureBlocking(false);
                /**
                 * 将通道注册到通道管理器,并为该通道注册SelectionKey.OP_ACCEPT事件
                 * 注册该事件后,当事件到达后,selector.select()会返回,
                 * 如果没有到达selector.select()会阻塞
                 */
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true){
                    //
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> set = serverSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();

                        while (keyIterator.hasNext()) {
                            try {
                                SelectionKey key = keyIterator.next();

                                //检查是否是一个就绪的,可以被接受的客户端请求连接
                                if (key.isAcceptable()) {
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector,SelectionKey.OP_READ);
                                }
                            } finally {
                                keyIterator.remove();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            while (true) {
                try {
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> set = clientSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            //检查套接字是否已经准备好读取数据
                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer)
                                    .toString());
                                } finally {
                                    keyIterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
