package com.springboot.cli.controller;


import com.github.pagehelper.PageInfo;
import com.springboot.cli.domain.User;
import com.springboot.cli.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiDing
 * @since 2022-04-11
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final IUserService iUserService;

    /**
     * 动态条件查询用户
     */
    @GetMapping("list")
    public List<User> listUser(User user) {
        return iUserService.listUser(user);
    }

    /**
     * 关联查询
     *
     * @param userId  可为空
     * @param account 可为空
     */
    @GetMapping("list/userAndArea")
    public List<HashMap<String, Object>> listUserAndArea(Long userId, String account) {
        return iUserService.listUserAndArea(userId, account);
    }

    /**
     * 动态条件查询用户(分页)
     */
    @GetMapping("list/page")
    public PageInfo<User> listUser(User user, Integer pageNum, Integer pageSize) {
        return iUserService.listUserPage(user, pageNum, pageSize);
    }

    /**
     * 通过账号删除用户
     */
    @GetMapping("del/account")
    public boolean listUser(String account) {
        return iUserService.delUser(account);
    }

}

