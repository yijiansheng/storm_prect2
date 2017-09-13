package com.stormio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Author: changdalin
 * Date: 2017/9/13
 * Description:
 **/
public class BaseRW {

    public static void main(String[] args) throws Exception {
        //readBytes();
        readAndWriteBytes();
    }


    public static void readBytes() throws Exception {
        RandomAccessFile file = new RandomAccessFile("D:\\tmp\\io\\aaa.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(24);
        /**
         * date:2017/9/13
         * description:重点是 用channel 读到buffer里面
         */
        int bytesRead = channel.read(buffer);
        String s = "";
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buffer.flip();
            while (buffer.hasRemaining()) {
                s += (char) buffer.get();
                //System.out.print((char) buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        file.close();
        System.out.println(s);
    }


    /**
     * date:2017/9/13
     * description:主要就是操作一个buffer,read和write这个buffer
     */
    public static void readAndWriteBytes() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("D:\\tmp\\io\\aaa.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("D:\\tmp\\io\\bbb.txt", "rw");
        FileChannel rchannel = readFile.getChannel();
        FileChannel wchannel = writeFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);
        int bytesRead = rchannel.read(buffer);

        /**
         * date:2017/9/13
         * description:可以调整位置。
         * channel.position理解成这个文件内容的position
         */
        while (bytesRead != -1) {
            System.out.println("rchannel的position:" + rchannel.position());
            System.out.println("buffer的position" + buffer.position());
            //buffer.position(3);
            buffer.flip();
            //直接写是覆盖
            wchannel.write(buffer);
            System.out.println("wchannel的position:" + wchannel.position());
            //wchannel.position(wchannel.position() + 5);
            buffer.clear();
            bytesRead = rchannel.read(buffer);
        }
        rchannel.close();
        wchannel.close();
    }
}