package com.example.erp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

@SpringBootTest
class ErpApplicationTests {

    // BCryptPasswordEncoder是Spring Security提供的一个密码加密工具类
    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encode = encoder.encode("123456");
//        String encode2 = encoder.encode("123456");
//        System.out.println(encode);
//        System.out.println(encode2);
        System.out.println(encoder.matches("123456",
                "$2a$10$E6LjAcDKrihXyUbnNla9eucGS3ynjtBsGw6vO/84nBWI2kEOOnKD6"));
    }

    // Base64是一种用64个字符来表示任意二进制数据的方法
    @Test
    public void testBase64() {
        String jwtToken =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTE5MDMyMDUsImlhdCI6MTcxMTI5ODQwNSwidXNlcm5hbWUiOiJhZG1pbiJ9.XiB1FXaiO6sftFZHRan3Z15qshIF8vo33skTlYDE_IM";
        String[] split = jwtToken.split("\\.");
        for (int i = 0; i < split.length - 1; i++) {
            String s = split[i];
            byte[] decode = Base64.getDecoder().decode(s);
            System.out.println(new String(decode));
        }
    }
}
