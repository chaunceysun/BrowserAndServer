package server;

/**
 * 存储响应信息
 *
 * @author chauncey
 */
public class HttpServletResponse {
    private StringBuilder responseContent = new StringBuilder();

    public void write(String str) {
        responseContent.append(str);
    }

    public String getResponseContent() {
        return responseContent.toString();
    }
}
