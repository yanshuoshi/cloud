package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.base.BaseException;
import com.common.utils.UploadUtil;
import com.yss.baseservice.api.system.request.StudentImportReq;
import com.yss.baseservice.api.system.request.StudentListReq;
import com.yss.baseservice.application.system.dto.StudentDto;
import com.yss.baseservice.application.system.dto.StudentExportDto;
import com.yss.baseservice.application.system.dto.StudentForAppDto;
import com.yss.baseservice.domain.system.entity.Student;
import com.yss.baseservice.domain.system.mapper.StudentMapper;
import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生领域层
 *
 * @author lijianbin
 * @date 2021-09-17 13:56
 **/
@Service
public class StudentDomainService extends ServiceImpl<StudentMapper, Student> {
    private final StudentMapper studentMapper;

    public StudentDomainService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * 学生分页列表
     *
     * @param studentNumber 用户名
     * @param studentName   姓名
     * @param studentSex    性别
     * @param rowBounds     分页
     * @return 学生列表
     */
    public List<StudentDto> selectStudentList(String studentNumber, String studentName, String studentSex, Integer organizationId, RowBounds rowBounds) {
        return studentMapper.selectStudentList(studentNumber, studentName, studentSex, organizationId, rowBounds);
    }

    /**
     * 保存学生信息
     *
     * @param studentNumber  用户名（学号）
     * @param studentName    姓名
     * @param studentSex     性别
     * @param studentMobile  手机号
     * @param organizationId 组织机构id
     * @param userId         创建者用户id
     */
    public String saveStudent(String studentNumber, String studentName, SexEnum studentSex, String studentMobile,
                              Integer organizationId, String userId, String headurl) {
        // 查看该用户名是否存在
        Student student = this.selectStudentByStudentNumber(studentNumber);
        if (student != null) {
            throw new BaseException(500, "用户已存在");
        }
        // 保存用户
        student = Student.createStudent(studentNumber, "123", studentName, studentSex, studentMobile, headurl, organizationId, userId);
        student.insert();
        return student.getStudentId();
    }

    /**
     * 根据学号查询用户
     *
     * @param studentNumber 学号
     * @return 用户
     */
    public Student selectStudentByStudentNumber(String studentNumber) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", studentNumber).eq("status", 1);
        return studentMapper.selectOne(queryWrapper);
    }

    /**
     * 修改学生信息
     *
     * @param studentId      用户id
     * @param studentName    姓名
     * @param studentSex     性别
     * @param studentMobile  手机号
     * @param organizationId 组织机构id
     */
    public void updateStudent(String studentId, String studentName, SexEnum studentSex, String studentMobile, Integer organizationId,
                              String headurl) {
        Student student = Student.updateStudent(studentId, studentName, studentSex, studentMobile, organizationId, headurl);
        student.updateById();
    }

    /**
     * 重置密码
     *
     * @param studentId 学生id
     */
    public void resetPassword(String studentId) {
        Student student = Student.resetPassword(studentId, "123");
        student.setUpdateTime(new Date());
        student.updateById();
    }

    /**
     * 修改学生密码
     *
     * @param studentId   学生id
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    public void changePassword(String studentId, String oldPassword, String newPassword) {
        Student student = this.studentMapper.selectById(studentId);
        student.setUpdateTime(new Date());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPassword, student.getStudentPassword())) {
            student.setStudentPassword(encoder.encode(newPassword));
            this.studentMapper.updateById(student);
        } else {
            throw new BaseException(500, "原密码不正确");
        }
    }

    /**
     * 删除学生
     *
     * @param studentId 用户id
     */
    public void removeStudent(String studentId) {
        Student student = Student.removeStudent(studentId);
        student.updateById();
    }

    /**
     * 批量导入用户
     *
     * @param list           用户信息
     * @param organizationId 组织机构id
     * @param userId         创建者用户id
     */
    public void importStudent(@Valid List<StudentImportReq> list, Integer organizationId, String userId) {
        // 验证excel是否存在重复数据
        Set<String> userSet = list.stream().map(StudentImportReq::getStudentNumber).collect(Collectors.toSet());
        if (userSet.size() != list.size()) {
            throw new BaseException(500, "存在重复导入,请检查excel!");
        }
        // 验证数据库中是否已存在
        Integer count = studentMapper.selectCount(new QueryWrapper<Student>().in("student_number", userSet).eq("status", 1));
        if (count > 0) {
            throw new BaseException(500, "账号已存在,请检查excel!");
        }
        // 保存
        List<Student> studentList = list.stream().map(req -> Student.createStudentForImport(req.getStudentNumber(), "123456",
                        req.getStudentName(), SexEnum.getValue(req.getStudentSex()), req.getStudentMobile(), organizationId, userId))
                .collect(Collectors.toList());
        studentMapper.insertAll(studentList);
    }

    /**
     * 导出学生信息
     *
     * @return 学生信息列表
     */
    public List<StudentExportDto> selectStudentExport(StudentListReq req) {
        return studentMapper.selectStudentExport(req.getStudentNumber(), req.getStudentName(), req.getStudentSex(), req.getOrganizationId());
    }


    /**
     * 批量导入照片
     *
     * @param files    导入文件
     * @param fileName 名称
     */
    public void headUrlImport(MultipartFile[] files, String fileName) {

        //该集合主要用于验证是否存在重复文件名的文件
        List<String> list = new ArrayList<>();
        if (null != files && files.length > 0) {
            for (MultipartFile file : files) {
                //获取到文件名
                String name = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
                list.add(name);
            }
            long count = list.stream().distinct().count();
            if (count < list.size()) {
                throw new BaseException(500, "存在重复文件");
            } else {
                List<Object> studentNameList = new ArrayList<>();
                for (MultipartFile file : files) {
                    long time = System.currentTimeMillis();
                    String name = file.getOriginalFilename();
                    name = name.substring(name.indexOf("."));
                    //获取学生名
                    String studentName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
                    //每个文件对应上传地址
                    String uploadFile = UploadUtil.uploadflie(file, StringUtils.isBlank(fileName) ? time + name : fileName + name);
                    //名称、头像地址集合
                    Map<String, String> map = new HashMap<>(16);
                    map.put("studentName", studentName);
                    map.put("headUrl", uploadFile);
                    studentNameList.add(map);
                }
                //更新头像
                this.studentMapper.updateHeadUrlAll(studentNameList);
            }
        } else {
            throw new BaseException(500, "文件为空!");
        }
    }


    /**
     * 选择同学
     *
     * @param organizationId 组织id
     * @param studentName    学生名
     * @return List<StudentForAppDto>
     */
    public List<StudentForAppDto> getStudentList(Integer organizationId, String studentName) {
        return this.studentMapper.getStudentList(organizationId, studentName);
    }

}
