package com.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Teacher;
import com.system.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教师Controller
 */
@Tag(name = "教师管理接口")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询教师列表
     */
    @Operation(summary = "分页查询教师列表")
    @GetMapping("/list")
    public Result<Page<Teacher>> getTeacherList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword) {
        Page<Teacher> page = teacherService.getTeacherPage(current, size, keyword);
        return Result.success(page);
    }

    /**
     * 根据ID查询教师详情
     */
    @Operation(summary = "查询教师详情")
    @GetMapping("/{id}")
    public Result<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }

    /**
     * 添加教师
     */
    @Operation(summary = "添加教师")
    @PostMapping
    public Result<String> addTeacher(@RequestBody Teacher teacher) {
        teacherService.addTeacher(teacher);
        return Result.success("添加成功");
    }

    /**
     * 更新教师信息
     */
    @Operation(summary = "更新教师信息")
    @PutMapping
    public Result<String> updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return Result.success("更新成功");
    }

    /**
     * 删除教师
     */
    @Operation(summary = "删除教师")
    @DeleteMapping("/{id}")
    public Result<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return Result.success("删除成功");
    }

}
