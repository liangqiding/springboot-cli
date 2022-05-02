package com.springboot.cli.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author : qiDing
 */
@Data
@Component
@ConfigurationProperties(prefix = MinioProperties.PRE)
public class MinioProperties {

    public static final String PRE = "minio";
    /**
     * endPoint是一个URL，域名，IPv4或者IPv6地址
     */
    private String endpoint;

    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    private String accessKey;

    /**
     * secretKey是你账户的密码
     */
    private String secretKey;

    /**
     * 如果是true，则用的是https而不是http,默认值是true
     */
    private Boolean secure;

    /**
     * 默认存储桶
     */
    private String bucketName;

    /**
     * 配置目录
     */
    private String configDir;

    @Bean
    public MinioClient getMinioClient() throws Exception {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
