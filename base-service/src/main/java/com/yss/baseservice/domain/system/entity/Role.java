package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.util.Date;

/**
 * 角色
 * @author lijianbin
 * @date 2021-09-17 15:53
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role extends Model<Role> {
    @TableId
    private Integer roleId;
    /**名称 */
    private String roleName;
    /** 角色标识 */
    private String roleKey;
    /** 创建人 */
    private String createUserId;
    /** 创建时间 */
    private Date createTime;
    /** 0删除 1正常 */
    private Integer status;

    /**
     * 创建角色
     * @param roleName      名称
     * @param roleKey       角色标识
     * @param userId        创建用户id
     */
    public static Role createRole(String roleName, String roleKey, String userId) {
        return Role.builder().roleName(roleName).roleKey(roleKey).createTime(new Date())
                .createUserId(userId).status(1).build();
    }

    /**
     * 修改角色
     * @param id            主键id
     * @param roleName      名称
     * @param roleKey       角色标识
     */
    public static Role updateRole(Integer id, String roleName, String roleKey) {
        return Role.builder().roleId(id).roleName(roleName).roleKey(roleKey).build();
    }

    /**
     * 删除角色
     * @param id    主键id
     */
    public static Role removeRole(Integer id) {
        return Role.builder().roleId(id).status(0).build();
    }
}
