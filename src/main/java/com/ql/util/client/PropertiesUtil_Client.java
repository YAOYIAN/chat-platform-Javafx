package com.ql.util.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil_Client {
    private static PropertiesUtil_Client propertiesUtil_Client = null;
    private final Properties properties;

    // 构造方法
    private PropertiesUtil_Client() {
        /*
         * 调用Properties类的输入流中读取属性列表的方法
         * properties.load（）
         */
        properties = new Properties();
        // 通过反射（输入流）接受数据库路径
        InputStream inputStream = PropertiesUtil_Client.class.getClassLoader().getResourceAsStream("config_client.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static PropertiesUtil_Client getPropertiesUtil() {
        if (propertiesUtil_Client == null) {
            propertiesUtil_Client = new PropertiesUtil_Client();
        }
        return propertiesUtil_Client;
    }

    // 对外通过key得到类Value值得方法
    public String getValue(String key) {
        // 用指定的键在类中搜索属性。
        return properties.getProperty(key);
    }

}
