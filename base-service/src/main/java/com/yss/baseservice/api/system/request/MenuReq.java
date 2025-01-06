package com.yss.baseservice.api.system.request;

import com.yss.baseservice.infrastructure.common.ienum.MenuEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单请求类
 * @author wangqi
 * @date 2021/11/3
 */
@Data
public class MenuReq {

    /** 父级id */
    @NotNull(message = "父级id不能为空")
    private Integer parentId;
    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    /** 路由地址 */
    @NotBlank(message = "路由地址不能为空")
    private String path;
    /**菜单类型 M-目录;C-菜单;F-按钮） */
    @NotNull(message = "菜单类型为空")
    private MenuEnum menuType;
    /** 权限标识 */
    @NotNull(message = "权限标识不能为空")
    private String permissionKey;
    /** 排序*/
    private Integer sort;


}
