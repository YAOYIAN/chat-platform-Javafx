package com.ql.util.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            //获取明文字节数组
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("No Such Algorithm.");
        }
        String md5code = new BigInteger(1,secretBytes).toString(16);
        for(int i=0;i < 32 - md5code.length();i ++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
