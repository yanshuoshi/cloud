package com.yss.baseservice.application.system;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.common.base.BaseException;
import com.common.config.SecurityUser;
import com.common.utils.ExcelUtils;
import com.common.utils.IpUtils;
import com.yss.baseservice.api.system.request.StudentImportReq;
import com.yss.baseservice.api.system.request.StudentListReq;
import com.yss.baseservice.api.system.request.StudentReq;
import com.yss.baseservice.api.system.request.StudentUpdateReq;
import com.yss.baseservice.application.system.dto.StudentDto;
import com.yss.baseservice.application.system.dto.StudentExportDto;
import com.yss.baseservice.application.system.dto.StudentForAppDto;
import com.yss.baseservice.domain.system.LoginLogDomainService;
import com.yss.baseservice.domain.system.StudentDomainService;
import com.yss.baseservice.domain.system.entity.Student;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 学生应用层
 * @author lijianbin
 * @date 2021-09-17 13:44
 **/
@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class StudentApplication implements UserDetailsService {
    private final StudentDomainService studentDomainService;
    private final RedisTemplate<String, String> redisTemplate;
    private final LoginLogDomainService loginLogDomainService;


    /**
     * 学生分页列表
     * @param studentNumber 用户名
     * @param studentName   姓名
     * @param studentSex           性别
     * @param rowBounds 分页
     * @return  学生列表
     */
    public List<StudentDto> studentList(String studentNumber, String studentName, String studentSex, Integer organizationId, RowBounds rowBounds) {
        return studentDomainService.selectStudentList(studentNumber, studentName, studentSex,  organizationId, rowBounds);
    }

    /**
     * 保存学生信息
     * @param req       学生信息
     * @param userId    创建者用户id
     */
    public String studentSave(StudentReq req, String userId) {
        String s = studentDomainService.saveStudent(req.getStudentNumber(), req.getStudentName(), req.getStudentSex(), req.getStudentMobile(),
                req.getOrganizationId(), userId, req.getHeadurl());
        return s;
    }

    /**
     * 修改学生信息
     * @param req       学生信息
     */
    public void updateStudent(StudentUpdateReq req, String userId) {
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.eq("student_id", req.getStudentId());
        if(StringUtils.isNoneBlank(req.getStudentName())){
            studentUpdateWrapper.set("student_name", req.getStudentName());
        }
        if(req.getStudentSex() != null && !req.getStudentSex().equals("")){
            studentUpdateWrapper.set("student_sex", req.getStudentSex());
        }
        if(StringUtils.isNoneBlank(req.getStudentMobile())){
            studentUpdateWrapper.set("student_mobile", req.getStudentMobile());
        }
        if(StringUtils.isNoneBlank(req.getHeadurl())){
            studentUpdateWrapper.set("headurl", req.getHeadurl());
        }
        studentUpdateWrapper.set("update_time", new Date());
        if(req.getOrganizationId() != null){
            studentUpdateWrapper.set("organization_id", req.getOrganizationId());
        }

        studentDomainService.update(studentUpdateWrapper);
        // studentDomainService.updateStudent(req.getStudentId(), req.getStudentName(), req.getStudentSex(), req.getStudentMobile(),
        //         req.getOrganizationId() ,req.getHeadurl());
    }

    /**
     * 重置密码
     * @param studentId    学生id
     */
    public void studentPwdReset(String studentId) {
        studentDomainService.resetPassword(studentId);
    }

    /**
     * 修改学生密码
     * @param studentId 学生id
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    public void changePassword(String studentId,String oldPassword,String newPassword){
        this.studentDomainService.changePassword(studentId,oldPassword,newPassword);
    }

    /**
     * 删除学生
     * @param studentId    用户id
     */
    public void removeStudent(String studentId) {
        studentDomainService.removeStudent(studentId);
    }

    /**
     * 批量导入学生信息
     * @param file              excel文件
     * @param organizationId    组织机构id
     * @param userId            创建者用户id
     */
    public void importStudent(MultipartFile file, Integer organizationId, String userId) {
        List<StudentImportReq> list = ExcelUtils.importExcel(file, 0, 1, StudentImportReq.class);
        if (CollectionUtils.isNotEmpty(list)) {
            // 导入用户
            studentDomainService.importStudent(list, organizationId, userId);
        } else {
            throw new BaseException(500, "导入数据为空或数据有误!");
        }
    }

    /**
     * 导出学生信息
     * @return 学生列表
     */
    public List<StudentExportDto> studentExport(StudentListReq req) {
        return studentDomainService.selectStudentExport(req);
    }


    /**
     * 批量上传头像
     * @param files 上传文件
     * @param fileName 文件名
     */
    public void headUrlImport(MultipartFile[] files,String fileName){
        studentDomainService.headUrlImport(files,fileName);
    }


    /**
     * 选择同学
     * @param organizationId 组织id
     * @param studentName 学生名
     * @return List<StudentForAppDto>
     */
    public List<StudentForAppDto> getStudentList(Integer organizationId, String studentName){
        return this.studentDomainService.getStudentList(organizationId,studentName);
    }

    /**
     * Security登录用户验证
     * @param studentNumber  学号
     * @return  用户
     */
    @Override
    public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {
        Student student = studentDomainService.selectStudentByStudentNumber(studentNumber);
        if (student == null) {
            return new SecurityUser();
        }
        SecurityUser securityUser = SecurityUser.loginUser(student.getStudentId(), student.getStudentNumber(), student.getStudentPassword(), null);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        loginLogDomainService.saveLoginLog(student.getStudentName(), IpUtils.getV4IP(),"登录成功", IpUtils.getIpAddr(request),student.getStudentNumber(),2);
        redisTemplate.opsForValue().set(securityUser.getUsername(), JSON.toJSONString(securityUser), 24, TimeUnit.HOURS);
        return securityUser;
    }
}
