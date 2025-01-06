package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改角色
 * @author lijianbin
 * @date 2021-09-17 16:40
 **/
@Data
public class RoleUpdateReq {
    /** 主键id */
    @NotNull(message = "名称不能为空")
    private Integer roleId;
    /** 名称 */
    @NotBlank(message = "名称不能为空")
    private String roleName;
    /** 角色标识 */
    @NotBlank(message = "角色标识不能为空")
    private String roleKey;
}
