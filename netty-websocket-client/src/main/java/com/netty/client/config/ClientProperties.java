package com.netty.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 服务配置
 *
 * @author qiding
 */
@Configuration
@ConfigurationProperties(prefix = ClientProperties.PREFIX)
@Data
public class ClientProperties {

    public static final String PREFIX = "netty";

    /**
     * 客户端ip
     */
    private Integer clientPort;

    /**
     * 默认连接的服务器ip
     */
    private String serverIp;

    /**
     * 默认连接的服务器端口
     */
    private Integer serverPort;

}
