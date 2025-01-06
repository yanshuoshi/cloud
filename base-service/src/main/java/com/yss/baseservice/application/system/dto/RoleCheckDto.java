package com.yss.baseservice.application.system.dto;

import lombok.Data;

/**
 * 角色选择包装类
 * @author wangqi
 * @date 2021/11/3
 */
@Data
public class RoleCheckDto {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 是否选中
     */
    private boolean checked;
}
