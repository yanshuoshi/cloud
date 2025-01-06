package com.yss.baseservice.application.system.dto;

import lombok.Data;

/**
 * 角色标识权限url
 * @author lijianbin
 * @date 2021-09-18 14:13
 **/
@Data
public class RoleNumberPermissionUrlDto {
    /** 角色标识 */
    private String role;
    /** 权限url */
    private String permission;
}
