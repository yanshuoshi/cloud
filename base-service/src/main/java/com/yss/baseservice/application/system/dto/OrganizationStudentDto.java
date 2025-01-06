package com.yss.baseservice.application.system.dto;

import com.common.utils.Tree1;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 组织机构
 * @author lijianbin
 * @date 2021-09-17 09:39
 **/
@Data
@NoArgsConstructor
public class OrganizationStudentDto implements Tree1 {
    /** 主键id */
    private String id;
    /** 部门名称 */
    private String organizationName;
    /** 父级id */
    private String parentId;
    /** 子部门 */
    private List<Tree1> children;
    /** 学生 */
    private List<Map<String,Object>> stus;
    /** 1 学生 2 班级 */
    private Integer type;


}
