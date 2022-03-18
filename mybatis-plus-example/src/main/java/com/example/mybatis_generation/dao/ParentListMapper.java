package com.example.mybatis_generation.dao;

import com.example.mybatis_generation.domain.ParentList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis_generation.domain.vo.ParentListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qiDing
 * @since 2021-12-14
 */
@Mapper
public interface ParentListMapper extends BaseMapper<ParentList> {

    List<ParentListVo> listParentTree(Integer parent);

}
