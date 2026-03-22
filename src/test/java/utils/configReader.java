package utils;

import java.io.InputStream;
import java.util.Properties;

public class configReader {
    public static Properties properties=new Properties();

    static {
    try {
        InputStream input = configReader.class
                .getClassLoader()
                .getResourceAsStream("src/test/resources/config/config.properties");
        properties.load(input);
    }catch (Exception e){
         e.printStackTrace();
    }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }
}
