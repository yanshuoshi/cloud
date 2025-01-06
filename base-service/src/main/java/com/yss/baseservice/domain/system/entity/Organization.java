package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 组织机构
 * @author lijianbin
 * @date 2021-09-17 09:16
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("sys_organization")
@Builder
@Data
public class Organization extends Model<Organization> {
    @TableId
    private Integer organizationId;
    /** 部门名称 */
    private String organizationName;
    /** 父级id */
    private Integer parentId;
    /** 创建时间 */
    private Date createTime;
    /** 创建用户id */
    private String createUserId;
    /** 状态:1-正常;0-删除 */
    private Integer status;

    /**
     * 创建组织机构
     * @param parentId      父级id
     * @param organizationName          名称
     * @param userId        创建用户id
     */
    public static Organization createOrganization(Integer parentId, String organizationName,   String userId) {
        return Organization.builder().organizationId((int) IdWorker.getId()).parentId(parentId).organizationName(organizationName) .createTime(new Date()).createUserId(userId).status(1).build();
    }

    /**
     * 修改组织机构
     * @param organizationId            主键id
     * @param organizationName          名称
     */
    public static Organization updateOrganization(Integer organizationId, String organizationName,Integer parentId) {
        return Organization.builder().organizationId(organizationId).organizationName(organizationName).parentId(parentId).build();
    }

}
