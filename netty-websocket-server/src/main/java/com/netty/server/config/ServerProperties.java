package com.netty.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 服务配置
 *
 * @author qiding
 */
@Configuration
@ConfigurationProperties(prefix = ServerProperties.PREFIX)
@Data
public class ServerProperties {

    public static final String PREFIX = "netty.server";

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 服务器端口
     */
    private Integer port;

    /**
     * 传输模式linux上开启会有更高的性能
     */
    private boolean useEpoll;

}
