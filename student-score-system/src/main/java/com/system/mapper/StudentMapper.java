package com.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生Mapper
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
