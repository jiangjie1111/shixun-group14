package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Permission;
import com.example.demo.mapper.PermissionMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Resource
    PermissionMapper PermissionMapper;

    @PostMapping
    public Result<?> save(@RequestBody Permission Permission) {
        PermissionMapper.insert(Permission);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Permission Permission) {
        PermissionMapper.updateById(Permission);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        PermissionMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(PermissionMapper.selectById(id));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(PermissionMapper.selectList(null));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Permission::getName, search);
        }
        Page<Permission> PermissionPage = PermissionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(PermissionPage);
    }
}
