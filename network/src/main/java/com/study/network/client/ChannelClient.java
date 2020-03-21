package com.study.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: yuqi
 * @Date: 2020-03-20 11:14
 */
public class ChannelClient {
    public static void main(String[] args) throws IOException {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);

            while (true) {
                System.out.println("请输入:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                writeBuffer.clear();
                writeBuffer.put(br.readLine().getBytes());
                writeBuffer.flip();
                socketChannel.write(writeBuffer);

                readBuffer.clear();
                socketChannel.read(readBuffer);
                readBuffer.flip();
                System.out.println("client,read:"+new String(readBuffer.array()));
            }
        } catch (IOException e) {
        }
    }
}
