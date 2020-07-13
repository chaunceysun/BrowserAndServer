package controller;

import server.HttpServletRequest;
import server.HttpServletResponse;
import service.IndexService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IndexController {
    public void test(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> paramsMap = request.getParamsMap();
        //1.获取请求发过来所携带的参数
        Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
        }
        //2.调用业务层方法
        new IndexService();
        //3.将最终的业务层执行完毕的结果交还给服务器，让服务器写回给浏览器
    }
}
