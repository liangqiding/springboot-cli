package com.springboot.cli.dao;

import com.springboot.cli.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qiDing
 * @since 2022-04-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 关联查询
     *
     * @param userId  用户id（可为空）
     * @param account 账号（可为空）
     * @return List
     */
    List<HashMap<String, Object>> listUserAndArea(@Param("userId") Long userId, @Param("account") String account);
}
