package server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Chauncey 2020/7/26
 */
public class ServerFileReader {
    private static HashMap<String, String> map = new HashMap<>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("ChaunceyServer\\src\\server.properties"));
            Enumeration en = properties.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = properties.getProperty(key);
                map.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return map.get(key);
    }
}
