package com.yss.baseservice.application.system.dto;

import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Data;

/**
 * 学生详情
 * @author wangqi
 * @date 2021/12/13
 */
@Data
public class StudentForAppDto {

    /** 主键id */
    private String studentId;
    /** 用户名(登录名) */
    private String studentNumber;
    /** 姓名 */
    private String studentName;
    /** 性别:F-女;M-男 */
    private SexEnum studentSex;
    // /** 年龄*/
    // private Integer studentAge;
    /** 手机号 */
    private String studentMobile;
    /** 头像 */
    private String headurl;
    /** 组织机构id */
    private String organizationId;
    /** 组织机构名称 */
    private String organizationName;
    // /** 专业 */
    // private String studentSpeciality;
    /** 学校 */
    private String school;

    /** 性别枚举**/
    public String getStudentSex() {
        return studentSex.getValue();
    }
}
