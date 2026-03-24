package utils;

import java.io.InputStream;
import java.util.Properties;

public class configReader {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = configReader.class
                    .getClassLoader()
                    .getResourceAsStream("config/config.properties");

            if (input == null) {
                throw new RuntimeException("config.properties NOT FOUND ❌");
            }

            properties.load(input);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties ❌");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}