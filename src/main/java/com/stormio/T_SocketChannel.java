package com.stormio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Author: changdalin
 * Date: 2017/9/13
 * Description:
 **/
public class T_SocketChannel {


    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int n = selector.select();
            if (n == 0) continue;
            Iterator ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                if (key.isAcceptable()) {
                    SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
                    clntChan.configureBlocking(false);
                    //将选择器注册到连接到的客户端信道，
                    //并指定该信道key值的属性为OP_READ，
                    //同时为该信道指定关联的附件
                    clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(24));
                }
                if (key.isReadable()) {
                    System.out.println("isReadable");
                }
                if (key.isWritable() && key.isValid()) {
                    System.out.println("isWritable && isValid");
                }
                if (key.isConnectable()) {
                    System.out.println("isConnectable");
                }
                ite.remove();
            }
        }
    }
}
