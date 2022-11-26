package com.ql.util.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static PropertiesUtil propertiesUtil = null;
    private final Properties properties;

    // 构造方法
    private PropertiesUtil() {
        /*
         * 调用Properties类的输入流中读取属性列表的方法
         * properties.load（）
         */
        properties = new Properties();
        // 通过反射（输入流）接受数据库路径
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesUtil getPropertiesUtil() {
        if (propertiesUtil == null) {
            propertiesUtil = new PropertiesUtil();
        }
        return propertiesUtil;
    }

    // 对外通过key得到类Value值得方法
    public String getValue(String key) {
        // 用指定的键在类中搜索属性。
        return properties.getProperty(key);
    }

}
