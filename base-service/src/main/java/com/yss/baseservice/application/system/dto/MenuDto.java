package com.yss.baseservice.application.system.dto;

import com.common.utils.Tree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 菜单返回包装类
 * @author wangqi
 * @date 2021/11/3
 */
@Data
@NoArgsConstructor
public class MenuDto implements Tree {
    /**菜单id */
    private Integer id;
    /**名称 */
    private Integer parentId;
    /** 菜单名称 */
    private String menuName;
    /** 路由地址 */
    private String path;
    /** 菜单类型  M-目录;C-菜单;F-按钮） */
    private String menuType;
    /** 权限标识 */
    private String permissionKey;
    /** 序号 */
    private Integer sort;
    /** 创建时间 */
    private Date createTime;
    /** 创建人*/
    private String createUserName;
    /** 状态 */
    private Integer status;
    /** 子菜单 */
    private List<Tree> children;


    public MenuDto(Integer id, Integer parentId, String menuName, String path, String menuType, String permissionKey, Integer sort, Date createTime, String createUserName, Integer status, List<Tree> children) {
        this.id = id;
        this.parentId = parentId;
        this.menuName = menuName;
        this.path = path;
        this.menuType = menuType;
        this.permissionKey = permissionKey;
        this.sort = sort;
        this.createTime = createTime;
        this.createUserName = createUserName;
        this.status = status;
        this.children = children;
    }
}
