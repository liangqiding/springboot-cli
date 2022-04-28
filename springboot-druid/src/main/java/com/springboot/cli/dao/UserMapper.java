package com.springboot.cli.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cli.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiDing
 * @since 2022-04-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
