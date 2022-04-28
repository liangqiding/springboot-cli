package com.springboot.cli.service.impl;

import com.springboot.cli.domain.Area;
import com.springboot.cli.dao.AreaMapper;
import com.springboot.cli.service.IAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ding
 * @since 2022-04-28
 */
@Service
@RequiredArgsConstructor
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    private final AreaMapper areaMapper;

    @Override
    public List<Area> listChildrenTree(Integer parentId) {
        return areaMapper.listChildrenTree(parentId);
    }
}
