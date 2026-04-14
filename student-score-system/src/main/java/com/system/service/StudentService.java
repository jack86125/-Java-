package com.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.Student;

/**
 * 学生Service接口
 */
public interface StudentService extends IService<Student> {

    /**
     * 分页查询学生列表
     */
    Page<Student> getStudentPage(Long current, Long size, String keyword);

    /**
     * 添加学生
     */
    boolean addStudent(Student student);

    /**
     * 更新学生信息
     */
    boolean updateStudent(Student student);

    /**
     * 删除学生
     */
    boolean deleteStudent(Long id);

}
