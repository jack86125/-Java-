package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.entity.Student;
import com.system.entity.SysUser;
import com.system.exception.BusinessException;
import com.system.mapper.StudentMapper;
import com.system.service.StudentService;
import com.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 学生Service实现类
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Page<Student> getStudentPage(Long current, Long size, String keyword) {
        Page<Student> page = new Page<>(current, size);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like(Student::getName, keyword)
                    .or()
                    .like(Student::getStudentNo, keyword);
        }

        wrapper.orderByDesc(Student::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addStudent(Student student) {
        // 检查学号是否已存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentNo, student.getStudentNo());
        if (this.count(wrapper) > 0) {
            throw new BusinessException("学号已存在");
        }

        // 创建用户账号
        SysUser user = new SysUser();
        user.setUsername(student.getStudentNo());
        user.setPassword("123456"); // 默认密码
        user.setRealName(student.getName());
        user.setRole("student");
        user.setStatus(1);
        sysUserService.save(user);

        // 设置用户ID
        student.setUserId(user.getId());

        // 保存学生信息
        return this.save(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudent(Student student) {
        return this.updateById(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStudent(Long id) {
        Student student = this.getById(id);
        if (student == null) {
            throw new BusinessException("学生不存在");
        }

        // 删除学生信息
        this.removeById(id);

        // 删除关联的用户账号
        if (student.getUserId() != null) {
            sysUserService.removeById(student.getUserId());
        }

        return true;
    }

}
