package test;

import server.MyServer;

/**
 * @author Chauncey Sun
 * @create 2022/10/18 22:59
 */
public class ServerMain {
    public static void main(String[] args) {
        new MyServer().start();
        System.out.println("aaa");
    }
}
