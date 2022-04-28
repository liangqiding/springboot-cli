package com.springboot.cli;

import com.alibaba.fastjson.JSON;
import com.springboot.cli.dao.AreaMapper;
import com.springboot.cli.dao.UserMapper;
import com.springboot.cli.domain.Area;
import com.springboot.cli.domain.User;
import com.springboot.cli.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MybatisGenerationApplicationTests {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private IUserService userService;

    @Test
    void listChildrenTree() {
        List<Area> areas = areaMapper.listChildrenTree(1);
        System.out.println(JSON.toJSONString(areas));
    }

    @Test
    void listUser() {
        List<User> users = userService.listUser(new User());
        System.out.println(JSON.toJSONString(users));
    }

}
