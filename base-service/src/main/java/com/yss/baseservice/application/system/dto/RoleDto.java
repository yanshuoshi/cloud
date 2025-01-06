package com.yss.baseservice.application.system.dto;

import lombok.Data;

/**
 * 角色列表信息
 * @author lijianbin
 * @date 2021-09-17 16:19
 **/
@Data
public class RoleDto {
    /** 角色id */
    private Integer roleId;
    /**名称 */
    private String roleName;
    /** 角色标识 */
    private String roleKey;
    /** 创建人 */
    private String createUserName;
    /** 创建时间 */
    private String createTime;
}
