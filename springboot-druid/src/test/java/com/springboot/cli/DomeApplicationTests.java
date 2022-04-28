package com.springboot.cli;

import com.alibaba.fastjson.JSON;
import com.springboot.cli.domain.User;
import com.springboot.cli.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DomeApplicationTests {

    @Autowired
    IUserService userService;

    @Test
    void test() {
        List<User> list = userService.list();
        System.out.println(JSON.toJSONString(list));
    }

}
