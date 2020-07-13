package server;

import java.util.HashMap;

/**
 * 存储请求信息
 */
public class HttpServletRequest {
    private String content;
    private HashMap<String, String> paramsMap;

    public HttpServletRequest() {
    }

    public HttpServletRequest(String content, HashMap<String, String> paramsMap) {
        this.content = content;
        this.paramsMap = paramsMap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(HashMap<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }
}
