package com.yss.baseservice.api.system.request;

import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建学生
 * @author lijianbin
 * @date 2021-09-17 14:13
 **/
@Data
public class StudentReq {
    /** 用户名(登录名) */
    @NotBlank(message = "学号不能为空")
    private String studentNumber;
    /** 密码 */
    private String studentPassword;
    /** 姓名 */
    @NotBlank(message = "姓名不能为空")
    private String studentName;
    /** 性别:F-女;M-男 */
    @NotNull(message = "性别不能为空")
    private SexEnum studentSex;
    /** 手机号 */
    private String studentMobile;
    /** 头像 */
    private String headurl;
    /** 组织机构id */
    @NotNull(message = "组织机构id不能为空")
    private Integer organizationId;
}