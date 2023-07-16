package org.example.driverapplication.utils;


import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class EncryptionUtil {


    public static String getEncryptedPwd(String password){
        String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        return md5Hex;
    }
}
