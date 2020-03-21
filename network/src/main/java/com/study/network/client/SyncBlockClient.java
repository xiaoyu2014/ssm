package com.study.network.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author: yuqi
 * @Date: 2020-03-19 22:38
 */
public class SyncBlockClient{

    private String host;
    private int serverPort;

    public SyncBlockClient(String host, int serverPort) {
        this.host = host;
        this.serverPort = serverPort;
    }

    public Socket createSocket() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host,serverPort));
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        try {
            SyncBlockClient syncBlockClient = new SyncBlockClient("127.0.0.1", 8080);
            Socket socket = syncBlockClient.createSocket();
            while (true) {
                System.out.print("请输入: \t");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String sendMsg = br.readLine();
                socket.getOutputStream().write(sendMsg.getBytes());
                socket.getOutputStream().flush();
                System.out.println("client send success：" + sendMsg);

                byte [] bytes = new byte[512];
                socket.getInputStream().read(bytes);
                System.out.println("client received：" + new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
