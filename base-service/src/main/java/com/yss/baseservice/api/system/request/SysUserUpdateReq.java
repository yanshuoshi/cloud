package com.yss.baseservice.api.system.request;

import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改系统用户
 * @author wangqi
 * @date 2021/11/1
 */
@Data
public class SysUserUpdateReq {
    /** 主键id */
    @NotBlank(message = "主键id不能为空")
    private String userId;
    /** 真实姓名 */
    // @NotBlank(message = "真实姓名不能为空")
    private String realName;
    /** 性别:F-女;M-男 */
    // @NotNull(message = "性别不能为空")
    private SexEnum sex;
    /** 手机号 */
    private String mobile;
    /** 头像 */
    private String headurl;
    /** 组织机构id */
    // @NotNull(message = "组织机构id不能为空")
    private Integer organizationId;
    /** 专业名称 */
    private String majorName;
}
