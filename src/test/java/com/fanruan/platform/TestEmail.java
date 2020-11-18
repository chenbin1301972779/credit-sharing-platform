package com.fanruan.platform;
 


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zjt
 * @Date 2019年03月07 10:26
 */
public class TestEmail {
 
    @Test
    public void test(){
//        String str = "139107101983f743189-3675-4c8b-a8e6-4099b5fac19e";
//        String md5Hex = DigestUtils.md5Hex(str);
//        System.out.println(md5Hex);



File file = new File("D:\\913300007804762286_15966381082593632751584700256032.html");
        System.out.println(file.length());

//        EmailEntity email = new EmailEntity();
//        email.setUserName("chenkanglin198904@163.com");
//        email.setPassword("EFFTMMYKZSHOOCSZ");
//        email.setHost("smtp.163.com");
//        email.setPort(25);
//        email.setFromAddress("chenkanglin198904@163.com");
//        email.setToAddress("717045607@qq.com");
//        email.setSubject("这是一封测试邮件!!!!");//标题
//        email.setContext("看看这是什么");//内容
//        email.setContextType("text/html;charset=utf-8");
//        boolean flag = EmailSend.sendEmail(email);
//        System.err.println("邮件发送结果=="+flag);
        Map<String, Object> param = new HashMap<>();
        param.put("a","SSSSSSSSSS");
        param.put("ab","SSSSSSSSSS");
        param.put("ade","SSSSSSSSSS");
        param.put("aaCode","SSSSSSSSSS");
        param.put("areCode","SSSSSSSSSS");
        param.put("areaCode","SSSSSSSSSS");

        param.put("areaCode","SSSSSSSSSS11111");

        System.out.println(param.toString());


    }

 
}