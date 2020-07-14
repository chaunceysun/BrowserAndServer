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
            InputStream is = socket.getInputStream();//基础字节流
            Reader reader = new InputStreamReader(is);//字节流转化成字符流
            BufferedReader br = new BufferedReader(reader);//高级流，可以读取一行
            String contentAndParams = br.readLine();
            parseContentAndParams(contentAndParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析sourceName?key=value&key=value...
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
        ServletController.findController(request, response);

        response2Browser(response);
    }

    /**
     * 处理controller或action或servlet
     * 用反射的方式查找控制层
     */
    private void findController(HttpServletRequest request, HttpServletResponse response) {
        //获取request对象中的请求名字
        String content = request.getContent();
        //参考配置文件
        Properties properties = new Properties();
        try {
            //问题1：findController与其他方法做的事情不一致，不应该出现在这里
            //问题2：多次读取文件造成性能下降，应该加个缓存
            //问题3：controller需要规则来统一管理
            //问题4：controller需要单例模式，并且被托管
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

    /**
     * 处理controller或action或servlet
     * 用低级的条件判断的方式查找控制层
     */
    private void findControllerOld(HttpServletRequest request, HttpServletResponse response) {
        if ("index".equals(request.getContent())) {
            IndexController ic = new IndexController();
            ic.service(request, response);
        }
    }

    /**
     * 响应
     */
    private void response2Browser(HttpServletResponse response) {
        System.out.println("返回消息");
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(response.getResponseContent());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
