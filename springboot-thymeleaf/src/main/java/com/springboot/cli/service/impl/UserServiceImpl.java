package com.springboot.cli.service.impl;

import com.springboot.cli.domain.User;
import com.springboot.cli.service.IUserService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author ding
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public boolean login(String username, String password, HttpSession session) {
        User user = this.getUser();
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            session.setAttribute("user", user);
            return true;
        }
        return false;
    }

    @Override
    public User getUser() {
        return new User()
                .setName("admin")
                .setUserId(1L)
                .setUsername("admin")
                .setPassword("123456");
    }
}
