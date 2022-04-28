package com.springboot.cli.controller;


import com.springboot.cli.domain.User;
import com.springboot.cli.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ding
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class IndexController {


    private final UserServiceImpl userService;
    /**
     * 查询所有用户
     */
    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }
}
