package com.springboot.cli.security;

import com.springboot.cli.security.hander.AccessFailure;
import com.springboot.cli.security.hander.JwtAuthFailure;
import com.springboot.cli.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * spring-security权限管理的核心配置
 * `@EnableGlobalMethodSecurity(prePostEnabled = true)` 开启权限注解控制
 * SecurityExpressionRoot 为权限el表达处理类
 *
 * @author ding
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SimpleUrlLogoutSuccessHandler logoutSuccess;

    private final JwtAuthFailure authFailure;

    private final AccessFailure accessFailure;

    private final JwtProvider jwtProvider;

    /**
     * 白名单
     */
    public final static String[] AUTH_WHITELIST = {"/login"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutSuccessHandler(logoutSuccess)
                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                // 异常处理器
                .and()
                .exceptionHandling()
                // jwt认证失败
                .authenticationEntryPoint(authFailure)
                // 拒绝访问
                .accessDeniedHandler(accessFailure)
                // 配置自定义的过滤器
                .and()
                .addFilter(new JwtFilter(authenticationManager(), jwtProvider))
                // 验证码过滤器放在UsernamePassword过滤器之前
                // .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                // 关闭跨域
                .cors().and().csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) {
        // 配置静态文件不需要认证
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
