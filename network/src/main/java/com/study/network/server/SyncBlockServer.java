package com.study.network.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: yuqi
 * @Date: 2020-03-19 22:46
 */
public class SyncBlockServer{

    private ServerSocket serverSocket = null;

    public SyncBlockServer(int serverPort) {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("server 启动：" + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }


    public static void main(String[] args) {
        SyncBlockServer syncBlockServer = new SyncBlockServer(8080);
        while (true) {
            try {
                Socket socket = syncBlockServer.getServerSocket().accept();
                System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort()
                        + "," + socket.getRemoteSocketAddress()
                        + "," + socket.getInetAddress().getHostAddress() + ":" + socket.getInetAddress().getHostName());

                new Thread(new SocketRunnable(socket)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class SocketRunnable implements Runnable{

        Socket socket;

        public SocketRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    byte [] bytes = new byte[512];
                    int size = socket.getInputStream().read(bytes);
                    if(size < 0){
                        break;
                    }
                    System.out.println("server 收到了：" + new String(bytes));

                    socket.getOutputStream().write("i'm server".getBytes());
                    socket.getOutputStream().flush();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
