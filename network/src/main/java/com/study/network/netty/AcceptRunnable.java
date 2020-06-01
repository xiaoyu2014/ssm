package com.study.network.netty;

import com.study.network.netty.eventloop.NioEventLoop;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author: yuqi
 * @Date: 2020-03-23 00:11
 */
public class AcceptRunnable implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private NioEventLoop workReactor;

    public AcceptRunnable(ServerSocketChannel serverSocketChannel, NioEventLoop workReactor) {
        try {
            this.selector = Selector.open();
            this.serverSocketChannel = serverSocketChannel;
            this.workReactor = workReactor;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel ss = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = ss.accept();
                        workReactor.submit(new IoRunnable(socketChannel));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
