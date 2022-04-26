package com.springboot.cli.controller;


import com.springboot.cli.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import java.io.File;

/**
 * @author ding
 */
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * 基础邮件
     * @param to 目标的邮箱地址
     */
    @GetMapping("/send")
    public String send(String to) {
        emailService.send(to, "测试邮件", "欢迎使用springboot-cli开发脚手架");
        return "发送成功！！";
    }

    /**
     * 发送附件
     * @param to 目标的邮箱地址
     */
    @GetMapping("/send/file")
    public String sendFile(String to) {
        File file = new File("D:\\test\\test.txt");
        emailService.send(to, "测试邮件", "欢迎使用springboot-cli开发脚手架", file);
        return "发送成功！！";
    }

    /**
     * 发送模板邮件
     * @param to 目标的邮箱地址
     */
    @GetMapping("/sendByTem")
    public String sendByTem(String to) {
        Context context = new Context();
        context.setVariable("code", 6666);
        emailService.send(to, "激活码模板邮件", context);
        return "发送成功！！";
    }

    /**
     * 发送模板邮件（带附件）
     * @param to 目标的邮箱地址
     */
    @GetMapping("/sendByTem/file")
    public String sendByTemFile(String to) {
        File file = new File("D:\\test\\test.txt");
        Context context = new Context();
        context.setVariable("code", 6666);
        emailService.send(to, "激活码模板邮件", context, file);
        return "发送成功！！";
    }
}
