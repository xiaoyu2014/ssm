package com.study.network.netty;

import com.study.network.netty.eventloop.NioEventLoop;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * @Author: yuqi
 * @Date: 2020-03-22 23:35
 */
public class Acceptor {
    private NioEventLoop mainReactor;
    private NioEventLoop workReactor;

    public Acceptor(NioEventLoop mainReactor, NioEventLoop workReactor) {
        this.mainReactor = mainReactor;
        this.workReactor = workReactor;
    }

    public void bind(int port) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        mainReactor.submit(new AcceptRunnable(serverSocketChannel, workReactor));
    }
}
