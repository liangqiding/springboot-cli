package com.springboot.cli.security;

import com.springboot.cli.security.hander.LogoutSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * spring-security权限管理的核心配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final AuthenticationSuccessHandler loginSuccessHandler;

    private final AuthenticationFailureHandler loginFailureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();//开启运行iframe嵌套页面

        http//1、配置权限认证
                .authorizeRequests()
                //配置不拦截路由
                .antMatchers("/500").permitAll()
                .antMatchers("/403").permitAll()
                .antMatchers("/404").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest() //任何其它请求
                .authenticated() //都需要身份认证
                .and()
                //2、登录配置表单认证方式
                .formLogin()
                // 自定义登录页面的url
                .loginPage("/login")
                // 设置登录账号参数，与表单参数一致
                .usernameParameter("username")
                // 设置登录密码参数，与表单参数一致
                .passwordParameter("password")
                // 告诉Spring Security在发送指定路径时处理提交的凭证，默认情况下，将用户重定向回用户来自的页面。登录表单form中action的地址，也就是处理认证请求的路径，
                // 只要保持表单中action和HttpSecurity里配置的loginProcessingUrl一致就可以了，也不用自己去处理，它不会将请求传递给Spring MVC和您的控制器，所以我们就不需要自己再去写一个/user/login的控制器接口了
                .loginProcessingUrl("/doLogin")
                // 登录成功后默认的跳转页面路径
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                // 使用自定义的成功结果处理器
                .successHandler(loginSuccessHandler)
                // 使用自定义失败的结果处理器
                .failureHandler(loginFailureHandler)
                .and()
                //3、注销
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccess())
                .permitAll()
                .and()
                //4、session管理
                .sessionManagement()
                // 失效后跳转到登陆页面
                .invalidSessionUrl("/login")
                // 单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
                //.maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy())
                // 单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
                //.maximumSessions(1).maxSessionsPreventsLogin(true) ;
                .and()
                // 禁用跨站csrf攻击防御
                .csrf()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置静态文件不需要认证
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
