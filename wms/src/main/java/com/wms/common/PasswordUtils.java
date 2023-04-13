package com.wms.common;

import cn.hutool.crypto.SecureUtil;

import java.util.Random;

/**
 * @Author: bo
 * @DATE: 2023/4/13 10:52
 **/
public class PasswordUtils {
    private static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',};

    public static String salt() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < sb.capacity(); i++) {
            sb.append(hex[random.nextInt(16)]);
        }
        return sb.toString();
    }

}
