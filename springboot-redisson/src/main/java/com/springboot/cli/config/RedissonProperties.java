package com.springboot.cli.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redisson yml配置
 *
 * @author : qiDing
 */
@Data
@Accessors(chain = true)
@Configuration
@ConfigurationProperties(prefix = RedissonProperties.PREFIX)
public class RedissonProperties {

    public static final String PREFIX = "redisson";
    /**
     * 连接超时，单位：毫秒
     */
    private Integer connectTimeout;

    /**
     * 密码
     */
    private String password;

    /**
     * 服务器地址
     */
    private String[] address;

    /**
     * 数据库序号,只有单机模式下生效
     */
    private Integer database;

    /**
     * 传输模式 linux上开启会有更高的性能
     */
    private Boolean useEpoll;

}
