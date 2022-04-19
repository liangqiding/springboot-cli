package com.example.mybatis_generation.controller;


import com.example.mybatis_generation.dao.ParentListMapper;
import com.example.mybatis_generation.domain.vo.ParentListVo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiDing
 * @since 2021-12-14
 */
@RestController
@RequiredArgsConstructor
public class ParentListController {

    private final ParentListMapper parentListMapper;

    /**
     * 递归树形查询,如地区
     */
    @GetMapping("/list/tree")
    public Object test(Integer id) {
        List<ParentListVo> parentListVos = parentListMapper.listParentTree(id);
        System.out.println(parentListVos);
        return parentListVos;
    }

}

