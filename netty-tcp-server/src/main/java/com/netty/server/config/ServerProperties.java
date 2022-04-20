package com.netty.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取YML中的服务配置
 *
 * @author ding
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
