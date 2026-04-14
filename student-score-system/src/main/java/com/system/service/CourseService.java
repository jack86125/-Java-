package com.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.Course;

/**
 * 课程Service接口
 */
public interface CourseService extends IService<Course> {

    /**
     * 分页查询课程列表
     */
    Page<Course> getCoursePage(Long current, Long size, String keyword);

}
