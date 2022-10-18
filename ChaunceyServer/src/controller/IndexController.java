package controller;

import server.HttpServlet;
import server.HttpServletRequest;
import server.HttpServletResponse;
import service.IndexService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Chauncey Sun
 * @create 2022/10/18 23:01
 */
public class IndexController extends HttpServlet {

    /**
     * 可以指向一个文件，把文件的内容（HTML）写回给浏览器，浏览器接到后解析
     *
     * @param request
     * @param response
     */
    public void service1(HttpServletRequest request, HttpServletResponse response) {
        response.sendRedirect("index.view");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> paramsMap = request.getParamsMap();
        //1.获取请求发过来所携带的参数
        Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
        StringBuilder responseText = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            responseText.append("key:" + entry.getKey() + ",value:" + entry.getValue() + "  ");
        }
        System.out.println(responseText);
        //2.调用业务层方法
        new IndexService();
        //3.将最终的业务层执行完毕的结果交还给服务器，让服务器写回给浏览器
        response.write(responseText.toString());
        response.write("<br>");
        response.write("********<br>");
        response.write("**银行**<br>");
        response.write("********<br>");
        response.write("请输入账号<br>");
        response.write("请输入密码<br>");
    }
}
