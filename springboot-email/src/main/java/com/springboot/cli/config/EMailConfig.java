package com.springboot.cli.config;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = EMailConfig.PRE)
public class EMailConfig {

    public final static String PRE = "email";

    private String host;
    private Integer port;
    private String from ;
    private String pass;

    public MailAccount getAccount() {
        MailAccount account = new MailAccount();
        account.setAuth(true);
        account.setHost(host);
        account.setPort(port);
        account.setFrom(from);
        account.setUser(from);
        account.setPass(pass);
        return account;
    }
}
