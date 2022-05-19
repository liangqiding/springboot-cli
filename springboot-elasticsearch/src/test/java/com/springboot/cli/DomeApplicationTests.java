package com.springboot.cli;

import com.alibaba.fastjson.JSON;
import com.springboot.cli.entity.User;
import com.springboot.cli.service.impl.EsUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
class DomeApplicationTests {

    @Autowired
    EsUserServiceImpl esUserService;

    /**
     * 创建索引
     */
    @Test
    void createIndex() throws IOException {
        esUserService.createIndex();
        esUserService.getIndex();
    }

    /**
     * 创建用户
     */
    @Test
    void createUser() throws IOException {
        User user = new User()
                .setUserId(1L)
                .setUsername("一鸣")
                .setAccount("admin1")
                .setCreatedDate(new Date())
                .setPassword("123456");
        esUserService.createUser(user);
    }

    /**
     * 批量创建
     */
    @Test
    void createUserBulk() throws IOException {
        // 设置数据
        List<User> list = new ArrayList<>();
        list.add(new User().setUserId(2L).setUsername("二狗子").setAccount("admin2").setCreatedDate(new Date()));
        list.add(new User().setUserId(3L).setUsername("张三").setAccount("admin3").setCreatedDate(new Date()));
        list.add(new User().setUserId(4L).setUsername("李四").setAccount("admin4").setCreatedDate(new Date()));
        list.add(new User().setUserId(5L).setUsername("王五").setAccount("admin5").setCreatedDate(new Date()));
        esUserService.createUserBulk(list);
    }

    /**
     * 获取用户
     */
    @Test
    void getUser() throws IOException {
        User user = esUserService.getUser(1L);
        log.info("获取到用户为:{}", user);
    }

    /**
     * 查询用户
     */
    @Test
    void searchUser() throws IOException {
        ArrayList<User> users = esUserService.searchUser();
        log.info("查询结果{}", JSON.toJSONString(users));
    }

    /**
     * 更新用户
     */
    @Test
    void updateUser() throws IOException {
        User user = new User()
                .setUserId(1L)
                .setUsername("一拳超人")
                .setAccount("admin1")
                .setCreatedDate(new Date())
                .setPassword("123456");
        esUserService.updateUser(user);
    }

    @Test
    void deleteUser() throws IOException {
        esUserService.deleteUser(1L);
    }

}
