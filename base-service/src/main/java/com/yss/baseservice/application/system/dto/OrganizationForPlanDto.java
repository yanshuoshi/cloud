package com.yss.baseservice.application.system.dto;

import lombok.Data;

/**
 * 返回组织包装类（考试计划组织列表使用）
 * @author wangqi
 * @date 2021/11/26
 */
@Data
public class OrganizationForPlanDto {

    /**
     * 组织id
     */
    private Integer organizationId;
    /**
     * 组织名称
     */
    private String  organizationName;
}
