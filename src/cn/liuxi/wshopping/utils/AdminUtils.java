package cn.liuxi.wshopping.utils;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AdminUtils {

    private static String username;
    private static String password;
    private static String prefix;

    static {


        //加载配置文件
        InputStream in = JedisPoolUtils.class.getClassLoader().getResourceAsStream("admin.properties");
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

       username = pro.getProperty("admin.username");
       password = pro.getProperty("admin.password");
       prefix = pro.getProperty("admin.prefix");

    }


    public static String getUsername() {
        return username;
    }


    public static String getPassword() {
        return MD5Utils.md5(prefix+password);
    }

    public static String getPrefix() {
        return prefix;
    }


}
