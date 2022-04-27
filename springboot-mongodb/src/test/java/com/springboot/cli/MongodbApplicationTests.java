package com.springboot.cli;

import com.alibaba.fastjson.JSON;
import com.springboot.cli.entity.User;
import com.springboot.cli.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class MongodbApplicationTests {

    @Autowired
    UserServiceImpl userService;

    /**
     * 生成模拟数据并保存到mongodb
     */
    @Test
    void saveUser() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User().setUserId((long) i)
                    .setUsername("王小锤" + i)
                    .setSex("男")
                    .setRemark("备注00" + i);
            users.add(user);
        }
        userService.insertUser(users);
    }

    /**
     * 查询
     */
    @Test
    void listUser() {
        // 1.无条件查询
        List<User> users1 = userService.listUser(new User());
        log.info("1.查询到用户：{}", JSON.toJSONString(users1));
        // 2.id查询
        List<User> users2 = userService.listUser(new User().setUserId(1L));
        log.info("2.查询到用户：{}", JSON.toJSONString(users2));
        // 3.多条件查询
        List<User> users3 = userService.listUser(new User().setUsername("王小").setSex("男"));
        log.info("3.查询到用户：{}", JSON.toJSONString(users3));
    }

    @Test
    void updateUser() {
        userService.updateUser(new User().setUserId(1L).setSex("女").setUsername("杨幂"));
    }

    @Test
    void delUser() {
        userService.deleteUser(1L);
    }


}
