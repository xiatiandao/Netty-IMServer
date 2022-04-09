package com.example.nettyserver.ioserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月02日 14:57:00
 */
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        new Thread(()->{
            while (true){
                try {
                    //阻塞方法获取新连接
                    Socket socket = serverSocket.accept();

                    // 为每一个新连接都创建一个新线程,负责读取数据
                    new Thread(()->{
                        int len;
                        byte[] bytes = new byte[1024];
                        try {
                            InputStream inputStream = socket.getInputStream();

                            while ((len = inputStream.read(bytes)) != -1){
                                System.out.println(new String(bytes,0,len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
