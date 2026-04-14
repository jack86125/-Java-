package com.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.Class;

/**
 * 班级Service接口
 */
public interface ClassService extends IService<Class> {

    /**
     * 分页查询班级列表
     */
    Page<Class> getClassPage(Long current, Long size, String keyword);

}
