package com.springboot.cli.email;

import cn.hutool.extra.mail.MailUtil;
import com.springboot.cli.config.EMailConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.File;



/**
 * 邮件服务
 *
 * @author ding
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final EMailConfig eMailConfig;

    private final TemplateEngine templateEngine;

    /**
     * 发送邮件
     *
     * @param to      目标邮箱
     * @param subject 标题
     * @param content 内容
     */
    public void send(String to, String subject, String content) {
        MailUtil.send(eMailConfig.getAccount(), to, subject, content, false);
    }

    /**
     * 发送邮件(带附件)读取模板
     *
     * @param to      目标邮箱
     * @param subject 标题
     * @param content 内容
     * @param files   附件（可选）
     */
    public void send(String to, String subject, String content, File... files) {
        MailUtil.send(eMailConfig.getAccount(), to, subject, content, false, files);
    }

    /**
     * 发送邮件-读取自定义模板
     *
     * @param to      目标邮箱
     * @param subject 标题
     * @param context 内容
     */
    public void send(String to, String subject, Context context) {
        String template = templateEngine.process("emailTemplate", context);
        MailUtil.send(eMailConfig.getAccount(), to, subject, template, true);
    }


    /**
     * 发送邮件-读取自定义模板（带附件）
     *
     * @param to      目标邮箱
     * @param subject 标题
     * @param context 内容
     * @param files   附件
     */
    public void send(String to, String subject, Context context, File... files) {
        String template = templateEngine.process("emailTemplate", context);
        MailUtil.send(eMailConfig.getAccount(), to, subject, template, true, files);
    }

}
