package com.springboot.cli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器路径
 *
 * @author lqd
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                // 拦截的路径
                .addPathPatterns("/**")
                // 开放的路径
                .excludePathPatterns("/login/**", "/token/validate");
    }
}
