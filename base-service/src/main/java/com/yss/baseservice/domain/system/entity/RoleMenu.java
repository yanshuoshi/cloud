package com.yss.baseservice.domain.system.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;


/**
 * 菜单角色
 * @author wangqi
 * @date 2021/11/1
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleMenu extends Model<RoleMenu> {

    /**角色id */
    private Integer roleId;
    /**菜单id */
    private Integer menuId;


    /**
     * 创建关联
     * @param menuId   菜单id
     * @param roleId   角色id
     */
    public static RoleMenu createConfiguration(Integer roleId, Integer menuId) {
        return RoleMenu.builder().roleId(roleId).menuId(menuId).build();
    }
}
