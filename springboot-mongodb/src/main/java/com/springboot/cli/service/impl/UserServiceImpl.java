package com.springboot.cli.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.springboot.cli.entity.User;
import com.springboot.cli.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final MongoTemplate mongoTemplate;

    @Override
    public void insertUser(User user) {
        mongoTemplate.insert(user);
    }

    @Override
    public void insertUser(List<User> user) {
        mongoTemplate.insert(user, User.class);
    }

    @Override
    public void updateUser(User user) {
        Query eq = Query.query(Criteria.where("userId").is(user.getUserId()));
        Update username = new Update()
                .set("username", user.getUsername())
                .set("sex", user.getSex());
        mongoTemplate.updateMulti(eq, username, User.class);
    }

    @Override
    public List<User> listUser(User user) {
        // 用来构建条件
        Criteria criteria = new Criteria();
        // 动态拼接条件，若为空，则不生效
        if (Objects.nonNull(user.getUserId())) {
            criteria.and("userId").is(user.getUserId());
        }
        if (StringUtils.hasLength(user.getSex())) {
            criteria.and("sex").is(user.getSex());
        }
        if (StringUtils.hasLength(user.getUsername())) {
            // 模糊查询username，CASE_INSENSITIVE：大小写不明感匹配
            Pattern pattern = Pattern.compile("^.*" + user.getUsername() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("username").regex(pattern);
        }
        return mongoTemplate.find(Query.query(criteria), User.class);
    }

    @Override
    public void deleteUser(Long userId) {
        mongoTemplate.remove(Query.query(Criteria.where("userId").is(userId)), User.class);
    }
}
