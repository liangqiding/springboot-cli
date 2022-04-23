package com.springboot.cli.service.impl;

import com.springboot.cli.entity.User;
import com.springboot.cli.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Override
    @Cacheable(value = "user-key")
    public User getUser(Long id) {
        return this.getUserInfo(id);
    }

    @Override
    @CacheEvict(value = "user-key", allEntries = true)
    public void delUser(Long id) {
        this.delUserInfo(id);
    }

    public void delUserInfo(Long id) {
        log.info("删除{}用户数据执行了！！", id);
    }

    /**
     * 模拟数据库查询
     */
    private User getUserInfo(Long id) {
        log.info("获取用户数据执行了！！");
        return new User().setUserId(id).setUsername("王小锤").setSex("男").setRemark("注解方式aop实现缓存");
    }
}
