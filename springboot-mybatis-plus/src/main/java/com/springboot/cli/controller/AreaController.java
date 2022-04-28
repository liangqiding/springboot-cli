package com.springboot.cli.controller;


import com.springboot.cli.domain.Area;
import com.springboot.cli.service.IAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ding
 * @since 2022-04-28
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("area")
public class AreaController {

    private final IAreaService iAreaService;

    /**
     * 树形递归查询地区信息
     */
    @GetMapping("list")
    public List<Area> list(Integer parentId) {
        return iAreaService.listChildrenTree(parentId);
    }
}

