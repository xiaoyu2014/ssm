package com.study.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: yuqi
 * @Date: 2020-03-20 12:49
 */
public class SyncUnblockClient {

    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(512);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(512);
    public void initClient(String host, int port) throws IOException{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(host, port));

        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws Exception{
        while (true){
            int count = selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isConnectable()){
                    connect(key);
                }else if(key.isReadable()){
                    read(key);
                }else if(key.isWritable()){
                    write(key);
                }
            }
        }
    }

    public void connect(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        if(channel.isConnectionPending()){
            channel.finishConnect();//显示地完成连接
        }
        System.out.println("client connected");
        write(key);
    }

    public void write(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();

        System.out.println("请输入:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String waitString = br.readLine();
        writeBuffer.clear();
        writeBuffer.put(waitString.getBytes());
        writeBuffer.flip();
        channel.write(writeBuffer);
        key.interestOps(SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        readBuffer.clear();
        channel.read(readBuffer);
        readBuffer.flip();
        System.out.println("read from server:"+new String(readBuffer.array()));
        key.interestOps(SelectionKey.OP_WRITE);
    }

    public static void main(String[] args) throws Exception{

        SyncUnblockClient client = new SyncUnblockClient();
        client.initClient("localhost", 8080);
        client.listen();
    }

}