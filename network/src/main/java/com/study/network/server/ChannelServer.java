package com.study.network.server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * @Author: yuqi
 * @Date: 2020-03-20 11:14
 */
public class ChannelServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8000));

            while (true) {
                SocketChannel socketChannel = ssc.accept();
                new Thread(new ChannelRunnable(socketChannel)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ChannelRunnable implements Runnable{

        ByteBuffer readBuff = ByteBuffer.allocate(1024);
        ByteBuffer writeBuff = ByteBuffer.allocate(128);
        SocketChannel socketChannel;
        public ChannelRunnable(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    readBuff.clear();
                    socketChannel.read(readBuff);
                    readBuff.flip();
                    System.out.println("收到:" + new String(readBuff.array()));

                    writeBuff.clear();
                    writeBuff.put("received".getBytes());
                    writeBuff.flip();
                    socketChannel.write(writeBuff);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
