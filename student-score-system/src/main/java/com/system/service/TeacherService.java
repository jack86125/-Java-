package com.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.Teacher;

/**
 * 教师Service接口
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页查询教师列表
     */
    Page<Teacher> getTeacherPage(Long current, Long size, String keyword);

    /**
     * 添加教师
     */
    boolean addTeacher(Teacher teacher);

    /**
     * 更新教师信息
     */
    boolean updateTeacher(Teacher teacher);

    /**
     * 删除教师
     */
    boolean deleteTeacher(Long id);

}
