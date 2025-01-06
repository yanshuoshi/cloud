package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 * @author lijianbin
 * @date 2021-09-17 16:29
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("t_permission")
@Builder
@Data
public class Permission extends Model<Permission> {
    @TableId
    private Integer id;
    /** 父级id **/
    private Integer parentId;
    /** 菜单名称 */
    private String name;
    /** 权限编码 */
    private String number;
    /** 菜单路径 */
    private String url;
    /** 菜单图标 */
    private String icon;
    /** 是否是菜单 1是 0不是 */
    private Integer isMenu;
    /** 描述 */
    private String description;
    /** 优先级 */
    private Integer priority;

    /**
     * 创建权限
     * @param parentId      父级id
     * @param name          名称
     * @param number        标识
     * @param isMenu        1-菜单;0-目录;
     * @param url           地址
     * @param description   描述
     * @param priority      排序
     */
    public static Permission createPermission(Integer parentId, String name, String number, Integer isMenu, String url, String description, Integer priority) {
        return Permission.builder().parentId(parentId).name(name).number(number).isMenu(isMenu).url(url)
                .description(description).priority(priority).build();
    }

    /**
     * 修改权限
     * @param id            主键id
     * @param name          名称
     * @param number        标识
     * @param isMenu        1-菜单;0-目录;
     * @param url           地址
     * @param description   描述
     * @param priority      排序
     */
    public static Permission updatePermission(Integer id, String name, String number, Integer isMenu, String url, String description, Integer priority) {
        return Permission.builder().id(id).name(name).number(number).isMenu(isMenu).url(url).description(description)
                .priority(priority).build();
    }
}
