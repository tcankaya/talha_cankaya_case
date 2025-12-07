package config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props;
    static
    {
        try
        {
            props = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            props.load(fis);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static int getIntProperty(String key)
    {
        return Integer.parseInt(getProperty(key));
    }
}