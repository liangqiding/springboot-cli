package com.springboot.cli.security;

import com.springboot.cli.domain.MyUser;
import com.springboot.cli.exception.CaptchaInvalidException;
import com.springboot.cli.utils.CaptchaUtils;
import com.springboot.cli.utils.RequestUtils;
import com.springboot.cli.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 重写用户信息认证
 *
 * @author ding
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 模拟一个数据库用户
     * 账号 admin
     * 密码 123456
     */
    private final static HashMap<String, MyUser> USER_MAP = new LinkedHashMap<>() {
        {
            put("admin", new MyUser()
                    .setUserId(1L)
                    .setUsername("admin")
                    .setPassword(new BCryptPasswordEncoder().encode("123456"))
            );
        }
    };

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = RequestUtils.getHttpRequest();
        String code = request.getParameter("code");
        log.info("登录者账号为：{},验证码：code:{}", username, code);
        // 验证码是否正确
        if (!CaptchaUtils.verify(code)) {
            throw new CaptchaInvalidException("验证码错误");
        }
        // 通过userName获取用户信息,实战中把USER_MAP换成数据库获取即可
        MyUser user = USER_MAP.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        // 角色和权限都在这里添加，角色以ROLE_前缀，不是ROLE_前缀的视为权限,这里添加了ROLE_ADMIN角色和read、write权限
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,read,write");
        AuthUser authUser = new AuthUser(user.getUsername(), user.getPassword(), authorities);
        // 我们存放我们自定义的信息,如用户id,方便后续获取用户信息
        authUser.setUserId(user.getUserId());
        return authUser;
    }

}
