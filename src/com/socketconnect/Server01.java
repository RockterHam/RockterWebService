package com.socketconnect;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server01 {
    private ServerSocket serverSocket;
    public static void main(String[] args){
        Server01 server = new Server01();
        server.start();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(8989);
            System.out.println("启动服务器");
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    public void receive(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("客户端建立了连接");
            //获取请求协议
            InputStream is = client.getInputStream();
            byte[] datas = new byte[1024*512];
            int lenth = is.read(datas);
            String requestData = new String(datas,0,lenth);
            System.out.println(requestData);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("建立连接失败");
        }
    }

    public void stop(){

    }
}
