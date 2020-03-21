package com.study.network.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-20 11:31
 */
public class SyncUnblockServer {

    // 通道管理器
    private Selector selector;
    ByteBuffer readBuffer = ByteBuffer.allocate(512);
    ByteBuffer writeBuffer = ByteBuffer.allocate(512);

    public void initServer(int port) throws Exception {
        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置通道为 非阻塞
        serverChannel.configureBlocking(false);
        // 将该通道对于的serverSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 获得一通道管理器
        this.selector = Selector.open();
        // 将通道管理器和该通道绑定，并为该通道注册selectionKey.OP_ACCEPT事件
        // 注册该事件后，当事件到达的时候，selector.select()会返回，
        // 如果事件没有到达selector.select()会一直阻塞
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws Exception {
        System.out.println("start server");
        // 轮询访问selector
        while (true) {
            // 当注册事件到达时，方法返回，否则该方法会一直阻塞
            int count = selector.select();
            Iterator iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                // 删除已选的key 以防重复处理
                iterator.remove();
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel channel = server.accept();
        System.out.println(channel.getRemoteAddress());
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
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

    public static void main(String[] args) throws Throwable {
        SyncUnblockServer server = new SyncUnblockServer();
        server.initServer(8080);
        server.listen();
    }
}
