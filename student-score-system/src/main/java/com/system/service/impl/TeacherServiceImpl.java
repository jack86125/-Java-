package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.entity.SysUser;
import com.system.entity.Teacher;
import com.system.exception.BusinessException;
import com.system.mapper.TeacherMapper;
import com.system.service.SysUserService;
import com.system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 教师Service实现类
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Page<Teacher> getTeacherPage(Long current, Long size, String keyword) {
        Page<Teacher> page = new Page<>(current, size);
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like(Teacher::getName, keyword)
                    .or()
                    .like(Teacher::getTeacherNo, keyword);
        }

        wrapper.orderByDesc(Teacher::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeacher(Teacher teacher) {
        // 检查工号是否已存在
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getTeacherNo, teacher.getTeacherNo());
        if (this.count(wrapper) > 0) {
            throw new BusinessException("工号已存在");
        }

        // 创建用户账号
        SysUser user = new SysUser();
        user.setUsername(teacher.getTeacherNo());
        user.setPassword("123456"); // 默认密码
        user.setRealName(teacher.getName());
        user.setRole("teacher");
        user.setStatus(1);
        sysUserService.save(user);

        // 设置用户ID
        teacher.setUserId(user.getId());

        // 保存教师信息
        return this.save(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeacher(Teacher teacher) {
        return this.updateById(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTeacher(Long id) {
        Teacher teacher = this.getById(id);
        if (teacher == null) {
            throw new BusinessException("教师不存在");
        }

        // 删除教师信息
        this.removeById(id);

        // 删除关联的用户账号
        if (teacher.getUserId() != null) {
            sysUserService.removeById(teacher.getUserId());
        }

        return true;
    }

}
