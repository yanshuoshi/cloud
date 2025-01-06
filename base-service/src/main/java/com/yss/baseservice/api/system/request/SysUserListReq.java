package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;

/**
 * 用户列表
 * @author wangqi
 * @date 2021/11/1
 **/
@Data
public class SysUserListReq extends BasePageReq {
    /** 账号*/
    private String username;
    /**真实姓名*/
    private String realName;
    /**联系方式*/
    private String mobile;
    /** 性别 */
    private String sex;
    /**组织机构id*/
    private Integer organizationId;
}
