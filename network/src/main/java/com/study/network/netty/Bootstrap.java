package com.study.network.netty;

import com.study.network.netty.eventloop.NioEventLoop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @Author: yuqi
 * @Date: 2020-03-22 23:20
 */
public class Bootstrap {

    private NioEventLoop group;
    private SocketChannel socketChannel;

    public Bootstrap(NioEventLoop group) {
        this.group = group;
    }

    public void connect(String host, int port){
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
