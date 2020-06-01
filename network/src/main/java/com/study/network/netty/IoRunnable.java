package com.study.network.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: yuqi
 * @Date: 2020-03-22 23:46
 */
public class IoRunnable implements Runnable {

    private SocketChannel socketChannel;
    private Selector selector;
    ByteBuffer readBuffer = ByteBuffer.allocate(512);
    ByteBuffer writeBuffer = ByteBuffer.allocate(512);

    public IoRunnable(SocketChannel serverSocketChannel) throws IOException {
        this.socketChannel = serverSocketChannel;
        this.selector = Selector.open();
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        readBuffer.clear();
        int count = channel.read(readBuffer);
        readBuffer.flip();
        if (count > 0) {
            String msg = new String(readBuffer.array()).trim();
            System.out.println("server receive from client: " + msg);
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        writeBuffer.clear();
        writeBuffer.put("hello client, i'm server".getBytes());
        writeBuffer.flip();
        channel.write(writeBuffer);
        key.interestOps(SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        try {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ, SelectionKey.OP_WRITE);
            while (true) {
                selector.select();
                Iterator iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    // 删除已选的key 以防重复处理
                    iterator.remove();
                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
