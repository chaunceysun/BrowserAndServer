package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public void start() {
        ServerSocket server = null;
        try {
            System.out.println("====启动服务器====");
            //自己创建一个服务
            server = new ServerSocket(9999);
            //等待某一个客户端来连接
            while (true) {
                //等待某一个客户端过来连接
                Socket socket = server.accept();
                //启动一个线程，负责处理当前浏览器发送过来的消息
                new ServerHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
