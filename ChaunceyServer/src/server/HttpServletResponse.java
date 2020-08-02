package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

    /**
     * 让response读取一个文件，文件中的内容是响应信息
     *
     * @param path
     */
    public void sendRedirect(String path) {
        try {
            File file = new File("ChaunceyServer\\src\\file\\" + path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String value = reader.readLine();
            while (value != null) {
                responseContent.append(value);
                value = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
