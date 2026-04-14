package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.entity.Course;
import com.system.mapper.CourseMapper;
import com.system.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 课程Service实现类
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Override
    public Page<Course> getCoursePage(Long current, Long size, String keyword) {
        Page<Course> page = new Page<>(current, size);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like(Course::getCourseName, keyword)
                    .or()
                    .like(Course::getCourseNo, keyword);
        }

        wrapper.orderByDesc(Course::getCreateTime);
        return this.page(page, wrapper);
    }

}
