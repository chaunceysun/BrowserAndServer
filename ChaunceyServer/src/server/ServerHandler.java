package server;

import controller.IndexController;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Chauncey
 */
public class ServerHandler extends Thread {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        receiveRequest();
    }

    /**
     * 读取消息
     */
    private void receiveRequest() {
        System.out.println("读取消息");

        try {
            InputStream is = socket.getInputStream();//基础字符流
            Reader reader = new InputStreamReader(is);//字符流转化成字节流
            BufferedReader br = new BufferedReader(reader);//高级流
            String contentAndParams = br.readLine();
            parseContentAndParams(contentAndParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析
     * sourceName?key=value&key=value...
     */
    private void parseContentAndParams(String contentAndParams) {
        if (contentAndParams == null) {
            return;
        }
        String content = null;
        HashMap<String, String> paramsMap = new HashMap<>(16);
        int questionMarkLocation = contentAndParams.indexOf("?");
        if (questionMarkLocation != -1) {
            content = contentAndParams.substring(0, questionMarkLocation);
            String p = contentAndParams.substring(questionMarkLocation + 1);
            String[] params = p.split("&");
            for (String s : params) {
                String[] kv = s.split("=");
                paramsMap.put(kv[0], kv[1]);
            }
        } else {
            content = contentAndParams;
        }

        HttpServletRequest request = new HttpServletRequest(content, paramsMap);
        HttpServletResponse response = new HttpServletResponse();

        findController(request, response);
    }

    /**
     * 处理
     * controller或action或servlet
     */
    private void findController(HttpServletRequest request, HttpServletResponse response) {
        //获取request对象中的请求名字
        String content = request.getContent();
        //参考配置文件
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("ChaunceyServer\\src\\web.properties"));
            //通过配置文件找到对应的类名
            String realControllerName = properties.getProperty(content);
            //反射获取类
            Class<?> clazz = Class.forName(realControllerName);
            Object obj = clazz.newInstance();
            //反射获取方法
            Method method = clazz.getMethod("test", HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(obj, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void findControllerOld(HttpServletRequest request, HttpServletResponse response) {
        if ("index".equals(request.getContent())) {
            IndexController ic = new IndexController();
            ic.test(request, response);
        }
    }

    /**
     * 响应
     */
    private void response() {
    }
}
