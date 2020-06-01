package com.study.network.netty.eventloop;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: yuqi
 * @Date: 2020-03-22 23:21
 */
public class NioEventLoop {

    private ExecutorService loopThreads;
    private Selector selector;

    public NioEventLoop(int threads) {
        try {
            loopThreads = Executors.newFixedThreadPool(threads);
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submit(Runnable runnable){
        loopThreads.submit(runnable);
    }

}
