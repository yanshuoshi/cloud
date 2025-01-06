package com.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_student")
public class Student extends Model<Student> {
    @TableId(type = IdType.INPUT)
    private String studentId;
    /** 登录名(学号) */
    private String loginName;
    /** 密码 */
    private String password;
    /** 头像 */
    private String icon;
    /** 姓名 */
    private String name;
    /** 手机号 */
    private String mobile;
    /** 创建人id */
    private String createUser;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 修改时间 */
    private LocalDateTime updateTime;
    /** 学校id */
    private Integer schoolId;
    /** 专业id */
    private Integer majorId;
    /** 所属班级id */
    private Integer clbumId;
    /** 年级id */
    private Integer gradeId;
    /** 启用/禁用 1: 启用, 0: 禁用 */
    private Integer disabled;
    /** 1正常 0逻辑删除 */
    private Integer status;
    /** 身份证号码-备用 */
    private String idCard;
}
