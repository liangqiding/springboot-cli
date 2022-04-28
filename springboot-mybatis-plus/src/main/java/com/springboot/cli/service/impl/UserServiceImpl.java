package com.springboot.cli.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.springboot.cli.domain.User;
import com.springboot.cli.dao.UserMapper;
import com.springboot.cli.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiDing
 * @since 2022-04-11
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public boolean addUser(User user) {
        return this.save(user);
    }

    @Override
    public boolean delUserById(Long userId) {
        return this.removeById(userId);
    }

    @Override
    public boolean delUser(String account) {
        QueryWrapper<User> eq = new QueryWrapper<User>()
                // 添加删除条件
                .eq("account", account);
        return this.remove(eq);
    }

    @Override
    public boolean updUser(User user) {
        StrUtil.isNotBlank(user.getPassword());
        UpdateWrapper<User> set = new UpdateWrapper<User>()
                .eq("user_id", user.getUserId())
                // 当传入的密码不为空，则更新
                .set(StrUtil.isNotBlank(user.getPassword()), "password", user.getPassword())
                // 当传入的用户名不为空，则更新
                .set(StrUtil.isNotBlank(user.getUsername()), "username", user.getUsername())
                // 更新时间
                .set("updated_date", new Date());
        return this.update(set);
    }

    @Override
    public List<User> listUser(User user) {
        QueryWrapper<User> eq = new QueryWrapper<User>()
                // 当用户名不为空，该条件生效，执行模糊查询
                .like(StrUtil.isNotBlank(user.getUsername()), "username", user.getUsername())
                // 当用户账号不为空，该条件生效
                .eq(StrUtil.isNotBlank(user.getAccount()), "account", user.getAccount())
                // 根据created_date倒序排序
                .orderBy(true, false, "created_date");
        return this.list(eq);
    }

    @Override
    public PageInfo<User> listUserPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = this.listUser(user);
        return new PageInfo<>(users);
    }

    @Override
    public List<HashMap<String, Object>> listUserAndArea(Long userId, String account) {
        return userMapper.listUserAndArea(userId, account);
    }


}
