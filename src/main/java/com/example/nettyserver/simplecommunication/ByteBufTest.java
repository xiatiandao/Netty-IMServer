package com.example.nettyserver.simplecommunication;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author admin
 * @version 1.0.0
 * @Description Netty数据载体ByteBuf 练习
 * @createTime 2022年04月05日 11:53:00
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        System.out.println("buffer(9, 100) : " + buffer);

        //write方法改变写指针,写完之后,指针未到capacity的时候,buffer仍然可写
        buffer.writeBytes(new byte[]{1,2,3,4});
        System.out.println("buf写入字节组 : " + buffer);

        //改变写指针,写完之后指针未到capacity的时候,buf仍然可写,写完int类型,写指针增加4
        buffer.writeInt(12);
        System.out.println("buf写int类型 : " + buffer);

        //write方法改变写指针,写完之后写指针等于capacity的时候,buffer不可写
        buffer.writeBytes(new byte[]{5});
        System.out.println("buf写入字节组 : " + buffer);

        //writer方法改变写指针,写的时候发现buffer不可写开始扩容,扩容之后capacity改变
        buffer.writeBytes(new byte[]{6});
        System.out.println("buf写入字节 6 : " + buffer);

        //get方法不改变读写指针
        System.out.println("getByte(3) return : " + buffer.getByte(3));
        System.out.println("getShort(3) return : " + buffer.getShort(3));
        System.out.println("getInt(3) return : " + buffer.getInt(3));
        System.out.println("getByte() return : " + buffer);

        //set方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1,0);
        System.out.println("setByte : " + buffer);

        //read方法改变读指针
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        System.out.println("readBytes(bytes.length) : "  + buffer);
    }
}
