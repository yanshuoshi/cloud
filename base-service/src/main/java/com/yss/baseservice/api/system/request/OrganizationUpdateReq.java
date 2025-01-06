package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增组织机构
 * @author lijianbin
 * @date 2021-09-17 09:32
 **/
@Data
public class OrganizationUpdateReq {
    /** 主键id */
    @NotNull(message = "id不能为空")
    private Integer id;
    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    private String organizationName;
    /** 上级菜单 */
    @NotNull(message = "上级菜单能为空")
    private Integer parentId;
}
