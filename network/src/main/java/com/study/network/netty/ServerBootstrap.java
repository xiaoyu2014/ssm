package com.study.network.netty;

import com.study.network.netty.eventloop.NioEventLoop;

import java.nio.channels.ServerSocketChannel;

/**
 * @Author: yuqi
 * @Date: 2020-03-22 23:21
 */
public class ServerBootstrap extends Bootstrap{

    private NioEventLoop childGroup;
    private Acceptor acceptor;

    public ServerBootstrap(NioEventLoop parentGroup, NioEventLoop childGroup) {
        super(parentGroup);
        this.childGroup = childGroup;
        acceptor = new Acceptor(childGroup, parentGroup);
    }

    public void bind(int port) throws Exception{
        acceptor.bind(port);
    }
}
