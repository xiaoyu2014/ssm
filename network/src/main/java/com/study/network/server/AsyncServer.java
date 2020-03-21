package com.study.network.server;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
/**
 * @Author: yuqi
 * @Date: 2020-03-21 02:11
 */
public class AsyncServer {

    private AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncServer(int port) throws Exception {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        serverSocketChannel.bind(inetSocketAddress);
    }

    public void listen() throws Exception{
        while (true) {
            Future<AsynchronousSocketChannel> accept = serverSocketChannel.accept();
            AsynchronousSocketChannel socketChannel = accept.get();
            System.out.println("接收到消息:"+socketChannel.getRemoteAddress());

            new Thread(new AsyncRunnable(socketChannel)).start();
        }
    }

    public static void main(String[] args) throws Exception{
        AsyncServer server = new AsyncServer(8080);
        server.listen();
    }

    static class AsyncRunnable implements Runnable{

        AsynchronousSocketChannel socketChannel;
        private ByteBuffer readBuffer = ByteBuffer.allocate(512);
        private ByteBuffer writeBuffer = ByteBuffer.allocate(512);

        public AsyncRunnable(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    readBuffer.clear();
                    Future<Integer> read = socketChannel.read(readBuffer);
                    while (!read.isDone()) {
                        Thread.sleep(10);
                    }
                    readBuffer.flip();
                    System.out.println("message:" + new String(readBuffer.array()));

                    writeBuffer.clear();
                    writeBuffer.put("i'm server".getBytes());
                    writeBuffer.flip();
                    Future<Integer> write = socketChannel.write(writeBuffer);
                    while (!write.isDone()) {
                        Thread.sleep(10);
                    }
                    System.out.println("服务器发送数据完毕.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}