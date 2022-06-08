package com.springboot.cli.service.impl;

import cn.hutool.core.util.StrUtil;
import com.springboot.cli.domain.MyUser;
import com.springboot.cli.service.UserService;
import com.springboot.cli.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 模拟数据库业务
 *
 * @author ding
 */
@Service
public class UserServiceImpl implements UserService {

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
                    .setPassword(SecurityUtils.passwordEncoder("123456"))
                    // 角色以ROLE_前缀
                    .setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"))
                    // 权限
                    .setAuths(Arrays.asList("read", "write"))
            );
        }
    };

    @Override
    public MyUser getUser(String username) {
        return USER_MAP.get(username);
    }


}
