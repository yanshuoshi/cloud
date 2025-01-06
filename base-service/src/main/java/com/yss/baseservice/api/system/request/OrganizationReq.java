package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增组织机构
 * @author lijianbin
 * @date 2021-09-17 09:32
 **/
@Data
public class OrganizationReq {
    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    private String organizationName;
    // /** 部门id */
    // @NotNull(message = "部门id不能为空")
    // private Integer organizationId;
    /** 父级id */
    private Integer parentId = 0;
}
