package com.uaa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uaa.entity.Student;
import com.uaa.mapper.StudentMapper;
import org.springframework.stereotype.Service;


/**
 */
@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {

    public Student getUserByUsername(String username) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student_number", username).eq("status", 1);
        return baseMapper.selectOne(wrapper);
    }

}
