package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;

import java.util.List;

/**
 * 查询学生列表（考试计划使用）
 * @author wangqi
 * @date 2021/12/27
 */
@Data
public class StudentListForPlanReq extends BasePageReq {

    /** 用户名 */
    private String studentNumber;
    /** 姓名 */
    private String studentName;
    /** 组织id */
    private Integer organizationId;
    /**学生id*/
    private List<String> studentIds;
}
