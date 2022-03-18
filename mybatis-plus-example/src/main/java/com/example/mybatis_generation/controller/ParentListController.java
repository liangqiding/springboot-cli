package com.example.mybatis_generation.controller;


import com.example.mybatis_generation.dao.ParentListMapper;
import com.example.mybatis_generation.domain.vo.ParentListVo;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ParentListController {

    private final ParentListMapper parentListMapper;

    @GetMapping("/get")
    public Object test(Integer id) {
        List<ParentListVo> parentListVos = parentListMapper.listParentTree(id);
        System.out.println(parentListVos);
        return parentListVos;
    }
}

