package com.yss.baseservice.application.system.dto;

import lombok.Data;

/**
 * 学生信息
 * @author lijianbin
 * @date 2021-09-17 11:46
 **/
@Data
public class StudentDto {
    /** 主键id */
    private String studentId;
    /** 用户名(登录名) */
    private String studentNumber;
    /** 姓名 */
    private String studentName;
    /** 性别:F-女;M-男 */
    private String studentSex;
    /**综合正确率 */
    private String correctRate;
    /**练习次数 */
    private Integer practiseCount;
    /** 手机号 */
    private String studentMobile;
    /** 头像 */
    private String headurl;
    /** 创建时间 */
    private String createTime;
    // /** 身份证号码 */
    // private String identityCard;
    /** 创建人名称 */
    private String createName;
    /** 组织机构id */
    private Integer organizationId;
    /** 组织机构名称 */
    private String organizationName;
}
