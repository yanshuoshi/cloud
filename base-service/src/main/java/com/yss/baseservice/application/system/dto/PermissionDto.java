package com.yss.baseservice.application.system.dto;

import com.common.utils.Tree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 权限
 * @author lijianbin
 * @date 2021-09-18 09:26
 **/
@Data
@NoArgsConstructor
public class PermissionDto implements Tree {
    /** 主键id */
    private Integer id;
    /** 父级id **/
    private Integer parentId;
    /** 菜单名称 */
    private String name;
    /** 权限编码 */
    private String number;
    /** 菜单路径 */
    private String url;
    /** 是否是菜单 1是 0不是 */
    private Integer isMenu;
    /** 描述 */
    private String description;
    /** 优先级 */
    private Integer priority;
    /** 子权限 */
    private List<Tree> children;

    public PermissionDto(Integer id, Integer parentId, String name, String number, String url, Integer isMenu, String description, Integer priority) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.number = number;
        this.url = url;
        this.isMenu = isMenu;
        this.description = description;
        this.priority = priority;
    }
}
