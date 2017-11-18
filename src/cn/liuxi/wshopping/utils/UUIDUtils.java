package cn.liuxi.wshopping.utils;

import java.util.UUID;

public class UUIDUtils {


    /**
     * 获得32位长度的UUID字符串
     *
     * @return
     */
    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-","");

    }


    /**
     * 获得64位长度的UUID字符串
     * @return
     */
    public static String getUUID64() {

        return getUUID() + getUUID();

    }
}

