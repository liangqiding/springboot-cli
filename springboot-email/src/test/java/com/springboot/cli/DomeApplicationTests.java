package com.springboot.cli;

import com.springboot.cli.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

import java.io.File;

@SpringBootTest
class DomeApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    void send1() {
        emailService.send("xxx@qq.com", "测试邮件", "欢迎使用springboot-cli开发脚手架");
    }

    @Test
    void send2() {
        File file = new File("D:\\test\\test.txt");
        emailService.send("xxx@qq.com", "测试邮件", "欢迎使用springboot-cli开发脚手架", file);
    }

    @Test
    void send3() {
        Context context = new Context();
        context.setVariable("code", 6666);
        emailService.send("xxx@qq.com", "激活码模板邮件", context);
    }

    @Test
    void send4() {
        File file = new File("D:\\test\\test.txt");
        Context context = new Context();
        context.setVariable("code", 6666);
        emailService.send("xxx@qq.com", "激活码模板邮件", context, file);
    }
}
