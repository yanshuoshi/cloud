package com.yss.baseservice.application.system.dto;

import com.common.utils.Tree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 组织机构
 * @author lijianbin
 * @date 2021-09-17 09:39
 **/
@Data
@NoArgsConstructor
public class OrganizationDto implements Tree {
    /** 主键id */
    private Integer id;
    /** 部门名称 */
    private String organizationName;
    /** 父级id */
    private Integer parentId;
    /** 创建时间 */
    private Date createTime;
    /** 子部门 */
    private List<Tree> children;
    /** 部门人数 */
    private Integer count;


}
