package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.entity.Class;
import com.system.mapper.ClassMapper;
import com.system.service.ClassService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 班级Service实现类
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Override
    public Page<Class> getClassPage(Long current, Long size, String keyword) {
        Page<Class> page = new Page<>(current, size);
        LambdaQueryWrapper<Class> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like(Class::getClassName, keyword)
                    .or()
                    .like(Class::getClassNo, keyword);
        }

        wrapper.orderByDesc(Class::getCreateTime);
        return this.page(page, wrapper);
    }

}
