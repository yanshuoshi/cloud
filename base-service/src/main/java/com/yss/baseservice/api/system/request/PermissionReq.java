package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增权限
 * @author lijianbin
 * @date 2021-09-18 09:44
 **/
@Data
public class PermissionReq {
    /** 父级id **/
    @NotNull(message = "父级id不能为空")
    private Integer parentId;
    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    /** 权限编码 */
    @NotBlank(message = "权限编码不能为空")
    private String number;
    /** 菜单路径 */
    private String url;
    /** 是否是菜单 1是 0不是 */
    private Integer isMenu = 1;
    /** 描述 */
    private String description;
    /** 优先级 */
    private Integer priority;
}
