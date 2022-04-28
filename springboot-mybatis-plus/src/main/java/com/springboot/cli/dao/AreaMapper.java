package com.springboot.cli.dao;

import com.springboot.cli.domain.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ding
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 查询地区树形
     *
     * @param parentId 父节点id
     * @return tree
     */
    List<Area> listChildrenTree(Integer parentId);

}
