package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联关系
 * @author wangqi
 * @date 2021/11/1
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
@Builder
@Data
public class SysUserRole extends Model<SysUserRole> {

    /** 用户id */
    private String userId;
    /** 角色id */
    private Integer roleId;

    /**
     * 创建关联
     * @param userId   用户id
     * @param roleId   角色id
     */
    public static SysUserRole createAssociating(String userId, Integer roleId) {
        return SysUserRole.builder().userId(userId).roleId(roleId).build();
    }
}
