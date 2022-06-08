package com.springboot.cli.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import com.springboot.cli.security.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * jwt 过滤器
 *
 * @author ding
 */
@Slf4j
public class JwtFilter extends BasicAuthenticationFilter {

    private final JwtProvider jwtProvider;

    public JwtFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtProvider.TOKEN_HEADER);
        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 注意登录等放行接口请求头不可带token，否则会进来认证
        if (StrUtil.isBlankOrUndefined(token)) {
            chain.doFilter(request, response);
            return;
        }
        JWT jwt = jwtProvider.decodeToken(token);
        if (jwt == null) {
            throw new JWTException("token 异常");
        }
        // 获取角色和权限
        List<GrantedAuthority> authority = this.getAuthority(jwt);
        // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwt, null, authority);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * 从token中获取用户权限
     */
    private List<GrantedAuthority> getAuthority(JWT jwt) {
        String authsStr = (String) jwt.getPayload(JwtProvider.AUTHORITY);
        if (!StrUtil.isBlank(authsStr)) {
            // 角色和权限都在这里添加，角色以ROLE_前缀，不是ROLE_前缀的视为权限
            return AuthorityUtils.commaSeparatedStringToAuthorityList(authsStr);
        }
        return null;
    }

}
