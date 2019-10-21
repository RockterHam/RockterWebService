package com.socketconnect;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server02 {
    String blank = " ";
    String CRLF = "\r\n";
    private ServerSocket serverSocket;
    public static void main(String[] args){
        Server02 server = new Server02();
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
        try(Socket client = serverSocket.accept();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            InputStream is = client.getInputStream();) {

            System.out.println("客户端建立了连接");
            //获取请求协议
            byte[] requestDatas = new byte[1024*512];
            int requestLenth = is.read(requestDatas);
            String requestData = new String(requestDatas,0,requestLenth);
            System.out.println(requestData);

            //响应返回
            //1.响应行：http/1.1 200 OK
            //2.响应头：(最后一行为空行)
            //Date: Thu, 16 Aug 2018 03:10:03 GMT
            //Server: Apache/2.4.18 (Win32) OpenSSL/1.0.2e mod_fcgid/2.3.9;charset=UTF-8
            //Content-Type: text/html;
            //Content-lenth:39773363
            //3.正文
            StringBuilder content = new StringBuilder();
            content.append("<html>");
            content.append("<head>");
            content.append("<title>");
            content.append("Title");
            content.append("</title>");
            content.append("</head>");
            content.append("<body>");
            content.append("Body");
            content.append("</body>");
            content.append("</html>");
            int responseLength = content.length();
            //响应头

            //Socket n = serverSocket.accept();
            StringBuilder responseInfo = new StringBuilder();
            responseInfo.append("HTTP/1.1").append(blank);
            responseInfo.append("200").append(blank);
            responseInfo.append("OK").append(CRLF);
            responseInfo.append("Date:").append(new Date()).append(CRLF);
            responseInfo.append("Server:").append("RockterCat Server/1.0V;charset=utf-8").append(CRLF);
            responseInfo.append("Content-type:text/html").append(CRLF);
            responseInfo.append("Content-lenth:").append(responseLength).append(CRLF);
            responseInfo.append(CRLF);
            responseInfo.append(content.toString());

            bw.write(responseInfo.toString());
            bw.flush();
            System.out.println(responseInfo.toString());

            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("建立连接失败");
        }
    }

    public void stop(){

    }

}
