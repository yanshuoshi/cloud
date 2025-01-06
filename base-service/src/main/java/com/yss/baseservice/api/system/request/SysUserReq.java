package com.yss.baseservice.api.system.request;

import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建系统用户
 * @author wangqi
 * @date 2021/11/1
 */
@Data
public class SysUserReq {

    /** 账号(登录名) */
    @NotBlank(message = "账号不能为空")
    private String username;
    /** 密码 */
    @NotBlank(message = "密码不能为空")
    private String password;
    /** 真实姓名 */
    private String realName;
    /** 性别:F-女;M-男 */
    @NotNull(message = "性别不能为空")
    private SexEnum sex;
    /** 联系方式 */
    private String mobile;
    /** 头像*/
    private String headurl;
    /** 组织机构id */
    @NotNull(message = "组织机构id不能为空")
    private Integer organizationId;
}
