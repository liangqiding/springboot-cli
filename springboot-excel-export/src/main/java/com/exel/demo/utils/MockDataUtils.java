package com.exel.demo.utils;

import com.exel.demo.entity.Course;
import com.exel.demo.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟数据
 *
 * @author Administrator
 */
public class MockDataUtils {

    /**
     * 模拟用户数据
     */
    public static List<User> getUserListMockData() {
        List<User> userInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User()
                    .setUserId(i)
                    .setUsername("张" + i)
                    .setAge(18)
                    .setSex("男");
            List<Course> courses = new ArrayList<>();
            courses.add(new Course().setCourseName("动漫必修1").setCreatedDate(new Date()).setCourseType("必修"));
            courses.add(new Course().setCourseName("动漫必修2").setCreatedDate(new Date()).setCourseType("必修"));
            courses.add(new Course().setCourseName("动漫必修3").setCreatedDate(new Date()).setCourseType("必修"));
            userInfos.add(user.setCourseList(courses));
        }
        return userInfos;
    }
}
