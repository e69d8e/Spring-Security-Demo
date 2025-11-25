package com.li.securitydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@SpringBootTest
class SecurityDemoApplicationTests {

    @Test
    void test() {
        System.out.println("test");
    }
    @Test
    void contextLoads() {
    }

    @Test
    void testPassword() {
        // 工作因子，默认值是10，最小值是4，最大值是31，值越大密码生成速度越慢
        PasswordEncoder encoder = new BCryptPasswordEncoder(4);
        //明文："password"
        //密文：result，即使明文密码相同，每次生成的密文也不一致
        String result = encoder.encode("password");
        System.out.println(result);

        //密码校验
        Assert.isTrue(encoder.matches("password", result), "密码不一致");
    }

    @Test
    void testPassword2() {
        PasswordEncoder encoder1 = new BCryptPasswordEncoder();
//        PasswordEncoder encoder2 = new BCryptPasswordEncoder(20);
        String result1 = encoder1.encode("123456");
//        String result2 = encoder2.encode("password");
        System.out.println(result1);
//        System.out.println(result2);
    }

}
