package com.example.nettyserver.ioserver;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月02日 14:57:00
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Socket socket = new Socket("127.0.0.1", 8000);

                while (true){
                    try {
                        socket.getOutputStream().write((new Date() + "hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
