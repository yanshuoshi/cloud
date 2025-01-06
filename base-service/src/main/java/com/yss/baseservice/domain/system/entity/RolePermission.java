package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关联
 * @author lijianbin
 * @date 2021-09-17 16:27
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_permission")
@Builder
@Data
public class RolePermission extends Model<RolePermission> {
    /** 角色id */
    private Integer rid;
    /** 权限id */
    private Integer pid;

    /**
     * 创建关联
     * @param rid   角色id
     * @param pid   权限id
     */
    public static RolePermission createAssociating(Integer rid, Integer pid) {
        return RolePermission.builder().rid(rid).pid(pid).build();
    }
}
