package com.springboot.cli.config;

import com.springboot.cli.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义登录授权
 *
 * @author ding
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 授权
     * 此处权限授予
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 在这里为每一个用户添加vip权限
        info.addStringPermission("vip");
        return info;
    }

    /**
     * 认证
     * 此处实现我们的登录逻辑，如账号密码验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取到token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从token中获取到用户名和密码
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        // 为了方便,这里模拟获取用户
        User user = this.getUser();
        if (!user.getUsername().equals(username)) {
            throw new UnknownAccountException("用户不存在");
        } else if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码错误");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 此处模拟用户数据
     * 实际开发中，换成数据库查询获取即可
     */
    private User getUser() {
        return new User()
                .setName("admin")
                .setUserId(1L)
                .setUsername("admin")
                .setPassword("123456");
    }
}
