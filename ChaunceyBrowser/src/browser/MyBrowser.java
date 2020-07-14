package browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyBrowser {
    private Scanner input = new Scanner(System.in);
    private Socket socket;

    public void open() {
        System.out.println("URL:");
//        String url = input.nextLine();
        String url = "localhost:9999/index?a=123456&b=789";
        parseURL(url);
    }

    /**
     * 解析URL
     * ip:port/folder/sourceName?key=value&key=value...
     *
     * @param url
     */
    private void parseURL(String url) {
        int colonIndex = url.indexOf(":");
        int slashIndex = url.indexOf("/");

        String ip = url.substring(0, colonIndex);
        int port = Integer.parseInt(url.substring(colonIndex + 1, slashIndex));
        String contentAndParams = url.substring(slashIndex + 1);

        createSocketAndSendRequest(ip, port, contentAndParams);
    }

    private void createSocketAndSendRequest(String ip, int port, String contentAndParams) {
        try {
            socket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(contentAndParams);
            out.flush();//把内容推送出去
            //浏览器等待响应信息
            receiveResponseContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveResponseContent() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseContent = reader.readLine();
            //解析响应信息并展示
            parseResponseContentAndShow(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseResponseContentAndShow(String responseContent) {
        System.out.println(responseContent);
    }
}
