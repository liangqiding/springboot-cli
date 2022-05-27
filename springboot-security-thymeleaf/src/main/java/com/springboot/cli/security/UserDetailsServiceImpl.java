package com.springboot.cli.security;

import com.springboot.cli.domain.MyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    private final PasswordEncoder passwordEncoder;

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
        // 通过userName获取用户信息,实战中把USER_MAP换成数据库获取即可
        MyUser user = USER_MAP.get(username);
        log.info("========={}", user);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        // 定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        AuthUser authUser = new AuthUser(user.getUsername(), user.getPassword(), authorities);
        authUser.setUserId(user.getUserId());
        return authUser;
    }
}
