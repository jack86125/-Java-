package com.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Student;
import com.system.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生Controller
 */
@Tag(name = "学生管理接口")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生列表
     */
    @Operation(summary = "分页查询学生列表")
    @GetMapping("/list")
    public Result<Page<Student>> getStudentList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword) {
        Page<Student> page = studentService.getStudentPage(current, size, keyword);
        return Result.success(page);
    }

    /**
     * 根据ID查询学生详情
     */
    @Operation(summary = "查询学生详情")
    @GetMapping("/{id}")
    public Result<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    /**
     * 添加学生
     */
    @Operation(summary = "添加学生")
    @PostMapping
    public Result<String> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return Result.success("添加成功");
    }

    /**
     * 更新学生信息
     */
    @Operation(summary = "更新学生信息")
    @PutMapping
    public Result<String> updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
        return Result.success("更新成功");
    }

    /**
     * 删除学生
     */
    @Operation(summary = "删除学生")
    @DeleteMapping("/{id}")
    public Result<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Result.success("删除成功");
    }

}
