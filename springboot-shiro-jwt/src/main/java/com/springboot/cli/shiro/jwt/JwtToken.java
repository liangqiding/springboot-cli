package com.springboot.cli.shiro.jwt;


import org.apache.shiro.authc.AuthenticationToken;


/**
 *
 * 自定义shiro的token
 *
 * @author ding
 */
public class JwtToken implements AuthenticationToken {

    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

