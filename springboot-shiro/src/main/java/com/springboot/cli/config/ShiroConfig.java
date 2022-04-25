package com.springboot.cli.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器
 *
 * @author ding
 */
@Configuration
public class ShiroConfig {

    /**
     * 无需认证就可以访问
     */
    private final static String ANON = "anon";
    /**
     * 必须认证了才能访问
     */
    private final static String AUTHC = "authc";
    /**
     * 拥有对某个资源的权限才能访问
     */
    private final static String PERMS = "perms";


    /**
     * 创建realm对象，需要自定义
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 创建安全管理器
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //绑定realm对象
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 授权过滤器
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        // 添加shiro的内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/index", ANON);
        filterMap.put("/userInfo", PERMS + "[vip]");
        filterMap.put("/table2", AUTHC);
        filterMap.put("/table3", PERMS + "[vip2]");
        bean.setFilterChainDefinitionMap(filterMap);
        // 设置跳转登陆页
        bean.setLoginUrl("/login");
        // 无权限跳转
        bean.setUnauthorizedUrl("/unAuth");
        return bean;
    }


    /**
     * Thymeleaf中使用Shiro标签
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}