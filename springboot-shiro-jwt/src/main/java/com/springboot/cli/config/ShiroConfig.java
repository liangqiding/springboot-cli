package com.springboot.cli.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * shiro核心管理器:三大核心对象：Subject、SecurityManager、Realm
 *
 * @author ding
 */
@Configuration
@Slf4j
public class ShiroConfig {

    /**
     * 告诉shiro不创建内置的session
     */
    @Bean
    public SubjectFactory subjectFactory() {
        return new ShiroDefaultSubjectFactory();
    }

    /**
     * 创建安全管理器
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(JwtRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    /**
     * 授权过滤器
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilter.setSecurityManager(securityManager);
        // 注册jwt过滤器,也就是将jwtFilter注册到shiro的Filter中，并在下面注册,指定除了login和logout之外的请求都先经过jwtFilter
        Map<String, Filter> filterMap = new HashMap<>(3) {
            {
                put("anon", new AnonymousFilter());
                put("jwt", new ShiroFilter());
                put("logout", new LogoutFilter());
            }
        };
        shiroFilter.setFilters(filterMap);
        // 拦截器
        Map<String, String> filterRuleMap = new LinkedHashMap<>(){
            {
                put("/login", "anon");
                // swagger放行
                put("/swagger-ui.html", "anon");
                put("/swagger-resources", "anon");
                put("/v2/api-docs", "anon");
                put("/webjars/springfox-swagger-ui/**", "anon");
                put("/configuration/security", "anon");
                put("/configuration/ui", "anon");
                // 任何请求都需要经过jwt过滤器
                put("/**", "jwt");
            }
        };
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

}