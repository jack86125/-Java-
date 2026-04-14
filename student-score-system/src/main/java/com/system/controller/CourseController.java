package com.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Course;
import com.system.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程Controller
 */
@Tag(name = "课程管理接口")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 分页查询课程列表
     */
    @Operation(summary = "分页查询课程列表")
    @GetMapping("/list")
    public Result<Page<Course>> getCourseList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword) {
        Page<Course> page = courseService.getCoursePage(current, size, keyword);
        return Result.success(page);
    }

    /**
     * 根据ID查询课程详情
     */
    @Operation(summary = "查询课程详情")
    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getById(id);
        return Result.success(course);
    }

    /**
     * 添加课程
     */
    @Operation(summary = "添加课程")
    @PostMapping
    public Result<String> addCourse(@RequestBody Course course) {
        courseService.save(course);
        return Result.success("添加成功");
    }

    /**
     * 更新课程信息
     */
    @Operation(summary = "更新课程信息")
    @PutMapping
    public Result<String> updateCourse(@RequestBody Course course) {
        courseService.updateById(course);
        return Result.success("更新成功");
    }

    /**
     * 删除课程
     */
    @Operation(summary = "删除课程")
    @DeleteMapping("/{id}")
    public Result<String> deleteCourse(@PathVariable Long id) {
        courseService.removeById(id);
        return Result.success("删除成功");
    }

}
