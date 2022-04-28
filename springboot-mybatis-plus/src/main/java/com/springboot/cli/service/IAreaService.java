package com.springboot.cli.service;

import com.springboot.cli.domain.Area;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ding
 * @since 2022-04-28
 */
public interface IAreaService extends IService<Area> {

    /**
     * 查询地区树形
     *
     * @param parentId 父节点id
     * @return tree
     */
    List<Area> listChildrenTree(Integer parentId);

}
