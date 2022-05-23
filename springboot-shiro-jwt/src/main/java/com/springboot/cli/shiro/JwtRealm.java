package com.springboot.cli.shiro;

import com.springboot.cli.shiro.jwt.JwtProvider;
import com.springboot.cli.shiro.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


/**
 * 处理token校验
 *
 * @author ding
 */
@Component
@RequiredArgsConstructor
public class JwtRealm extends AuthorizingRealm {

    private final JwtProvider jwtProvider;

    /**
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     */

    @Override
    public boolean supports(AuthenticationToken token) {
        // 这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    /**
     * 自定义授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 默认给一个user角色
        authorizationInfo.addRole("user");
        return authorizationInfo;
    }

    /**
     * 自定义认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = (String) authenticationToken.getPrincipal();
        // 解码token
        Claims claims = jwtProvider.decodeToken(jwt);
        if (claims == null) {
            throw new IncorrectCredentialsException("Authorization token is invalid");
        }
        // claims放入全局Subject中
        return new SimpleAuthenticationInfo(claims, jwt, "JwtRealm");
    }
}


