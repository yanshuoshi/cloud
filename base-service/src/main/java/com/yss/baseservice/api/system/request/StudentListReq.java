package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生列表查询
 * @author lijianbin
 * @date 2021-09-17 13:39
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentListReq extends BasePageReq {
    /**
     * 用户名
     */
    private String studentNumber;
    /**
     *  姓名
     */
    private String studentName;
    /**
     * 性别
     */
    private String studentSex;
    /**
     * 组织id
     */
    private Integer organizationId;
}
