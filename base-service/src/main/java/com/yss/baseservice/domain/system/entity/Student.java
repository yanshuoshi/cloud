package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * @author lijianbin
 * @date 2021-09-17 11:47
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("t_student")
@Builder
@Data
public class Student extends Model<Student> {
    /** 学生id */
    @TableId(type = IdType.INPUT)
    private String studentId;
    /** 用户名(登录名) */
    private String studentNumber;
    /** 密码 */
    private String studentPassword;
    /** 姓名 */
    private String studentName;
    /** 性别:F-女;M-男 */
    private SexEnum studentSex;
    /** 手机号 */
    private String studentMobile;
    /** 头像地址**/
    private String headurl;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
    /** 创建人id */
    private String createUser;
    /** 1正常 0逻辑删除 */
    private Integer status;
    /** 组织机构id */
    private Integer organizationId;

    /**
     * 创建用户
     * @param studentNumber     用户名
     * @param studentPassword   密码
     * @param studentName       姓名
     * @param studentSex        性别
     * @param studentMobile     手机号
     * @param organizationId    组织机构id
     * @param userId            创建者用户id
     */
    public static Student createStudent(String studentNumber, String studentPassword, String studentName, SexEnum studentSex ,String studentMobile,
                                          String headurl, Integer organizationId, String userId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return Student.builder().studentId(IdWorker.getIdStr()).studentNumber(studentNumber).studentPassword(encoder.encode(studentPassword)).studentName(studentName)
                .studentSex(studentSex).studentMobile(studentMobile) .organizationId(organizationId).headurl(headurl).updateTime(new Date())
                .createTime(new Date()).createUser(userId).status(1).build();
    }

    /**
     *  导入学生
     * @param studentNumber 学号
     * @param studentPassword 密码
     * @param studentName 学生姓名
     * @param studentSex 性别
     * @param studentMobile 电话
     * @param organizationId 组织id
     * @param userId 用户id
     * @return Student
     */
    public static Student createStudentForImport(String studentNumber, String studentPassword, String studentName, SexEnum studentSex  ,String studentMobile,
                                          Integer organizationId, String userId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return Student.builder().studentId(IdWorker.getIdStr()).studentNumber(studentNumber).studentPassword(encoder.encode(studentPassword)).studentName(studentName)
                .studentSex(studentSex).studentMobile(studentMobile) .organizationId(organizationId)
                .createTime(new Date()).createUser(userId).status(1).build();
    }

    /**
     * 更新用户
     * @param studentId        学生id
     * @param studentName      姓名
     * @param studentSex       性别
     * @param studentMobile    手机号
     * @param organizationId   组织机构id
     */
    public static Student updateStudent(String studentId, String studentName, SexEnum studentSex, String studentMobile,
                                        Integer organizationId ,String headurl) {
        return Student.builder().studentId(studentId).studentName(studentName).studentSex(studentSex).studentMobile(studentMobile).
                 organizationId(organizationId) .headurl(headurl) .build();
    }

    /**
     * 重置用户密码
     * @param studentId        学生id
     * @param studentPassword  密码
     */
    public static Student resetPassword(String studentId, String studentPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return Student.builder().studentId(studentId).studentPassword(encoder.encode(studentPassword)).build();
    }

    /**
     * 删除用户
     * @param studentId        学生id
     */
    public static Student removeStudent(String studentId) {
        return Student.builder().studentId(studentId).status(0).build();
    }



}
