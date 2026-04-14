package com.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Class;
import com.system.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 班级Controller
 */
@Tag(name = "班级管理接口")
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 分页查询班级列表
     */
    @Operation(summary = "分页查询班级列表")
    @GetMapping("/list")
    public Result<Page<Class>> getClassList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword) {
        Page<Class> page = classService.getClassPage(current, size, keyword);
        return Result.success(page);
    }

    /**
     * 根据ID查询班级详情
     */
    @Operation(summary = "查询班级详情")
    @GetMapping("/{id}")
    public Result<Class> getClassById(@PathVariable Long id) {
        Class clazz = classService.getById(id);
        return Result.success(clazz);
    }

    /**
     * 添加班级
     */
    @Operation(summary = "添加班级")
    @PostMapping
    public Result<String> addClass(@RequestBody Class clazz) {
        classService.save(clazz);
        return Result.success("添加成功");
    }

    /**
     * 更新班级信息
     */
    @Operation(summary = "更新班级信息")
    @PutMapping
    public Result<String> updateClass(@RequestBody Class clazz) {
        classService.updateById(clazz);
        return Result.success("更新成功");
    }

    /**
     * 删除班级
     */
    @Operation(summary = "删除班级")
    @DeleteMapping("/{id}")
    public Result<String> deleteClass(@PathVariable Long id) {
        classService.removeById(id);
        return Result.success("删除成功");
    }

}
