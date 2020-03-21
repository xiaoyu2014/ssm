package com.study.network.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @Author: yuqi
 * @Date: 2020-03-21 02:10
 */
public class AsyncClient {

    private String host;
    private int serverPort;
    public AsyncClient(String host, int serverPort) {
        this.host = host;
        this.serverPort = serverPort;
    }

    AsynchronousSocketChannel createSocketChannel() throws Exception {
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        Future<Void> connect = channel.connect(new InetSocketAddress(host, serverPort));
        while (!connect.isDone()) {
            Thread.sleep(10);
        }
        return channel;
    }

    public static void main(String[] args) {
        AsyncClient asyncClient = new AsyncClient("localhost", 8080);
        ByteBuffer readBuffer = ByteBuffer.allocate(512);
        ByteBuffer writeBuffer = ByteBuffer.allocate(512);
        try {
            AsynchronousSocketChannel socketChannel = asyncClient.createSocketChannel();

            System.out.println("请输入:");
            writeBuffer.clear();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            writeBuffer.put(br.readLine().getBytes());
            writeBuffer.flip();
            Future<Integer> write = socketChannel.write(writeBuffer);
            while (!write.isDone()) {
                Thread.sleep(10);
            }
            System.out.println("客户端发送数据完毕.");

            readBuffer.clear();
            Future<Integer> read = socketChannel.read(readBuffer);
            while (!read.isDone()) {
                Thread.sleep(10);
            }
            readBuffer.flip();
            System.out.println("接收服务器数据:" + new String(readBuffer.array(), 0, read.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
