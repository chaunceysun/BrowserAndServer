package server;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

//这个类的目的是为了管理  findController方法
//1.方法与之前服务器Handler做的事情不一致，抽离出来
//2.每一次找寻Controller类的时候都需要参考一下web.properties
//      读取文件性能会比较低，增加一个缓存机制
//3.每一个Controller类都是由findController方法来找寻
//      找到了Controller类的目的是为了执行里面的方法
//      让类中的方法有一个统一的规则----便于查找和使用
//4.发现Controller类与之前的Service和Dao相似，只有方法执行，没有属性
//      让Controller类的对象变成单例模式
public class ServletController {
    //一个缓存，用来存储web.properties里的对应关系
    private static HashMap<String, String> controllerNameMap = new HashMap<>();

    //创建一个静态块，在类加载的时候就读取配置文件到缓存（HashMap）中
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("ChaunceyServer\\src\\web.properties"));
            Enumeration<?> en = properties.propertyNames();
            while (en.hasMoreElements()) {
                String content = (String) en.nextElement();
                String realControllerName = properties.getProperty(content);
                controllerNameMap.put(content, realControllerName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理controller或action或servlet
     */
    private void findController(HttpServletRequest request, HttpServletResponse response) {
        String content = request.getContent();
        try {
            String realControllerName=controllerNameMap.get(0);

            Class<?> clazz = Class.forName(realControllerName);
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("test", HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(obj, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
