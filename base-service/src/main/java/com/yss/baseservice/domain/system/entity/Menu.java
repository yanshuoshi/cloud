package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yss.baseservice.infrastructure.common.ienum.MenuEnum;
import lombok.*;

import java.util.Date;

/**
 * 菜单实体类
 * @author wangqi
 * @date 2021/11/1
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu extends Model<Menu> {
    @TableId
    private Integer menuId;
    /**父级id */
    private Integer parentId;
    /** 菜单名称 */
    private String menuName;
    /** 路由地址 */
    private String path;
    /** 菜单类型 M-目录;C-菜单;F-按钮）*/
    private MenuEnum menuType;
    /** 权限标识 */
    private String permissionKey;
    /** 序号 */
    private Integer sort;
    /** 创建时间 */
    private Date createTime;
    /** 创建人id */
    private String createUserId;
    /** 状态 */
    private Integer status;


    /**
     * 创建菜单
     * @param parentId 父级id
     * @param menuName 菜单名称
     * @param path 路由地址
     * @param menuType 菜单类型
     * @param permissionKey 权限标识
     * @param sort 排序
     * @param userId 用户id
     * @return Menu
     */
    public static Menu createMenu(Integer parentId, String menuName, String path, MenuEnum menuType, String permissionKey,
                                        Integer sort, String userId) {
        return Menu.builder().parentId(parentId).menuName(menuName).path(path)
                .menuType(menuType).permissionKey(permissionKey).sort(sort)
                .createTime(new Date()).createUserId(userId).status(1).build();
    }

    /**
     * 修改菜单
     * @param menuId 菜单id
     * @param parentId 父级id
     * @param menuName 菜单名称
     * @param path 路由地址
     * @param menuType 菜单类型
     * @param permissionKey 权限标识
     * @param sort 序号
     * @return Menu
     */
    public static Menu updateMenu(Integer menuId, Integer parentId, String menuName, String path, MenuEnum menuType, String permissionKey, Integer sort) {
        return Menu.builder().menuId(menuId).parentId(parentId).menuName(menuName).path(path).menuType(menuType).permissionKey(permissionKey).sort(sort).build();
    }





}
