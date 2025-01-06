package com.yss.baseservice.application.system.dto;

import com.yss.baseservice.infrastructure.common.ienum.BusinessTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 * @author lijianbin
 * @date 2021-09-18 11:33
 **/
@Data
@Builder
@AllArgsConstructor
public class OperLogDto {
    /** 主键id */
    private String logId;
    /** 登录名(用户名) */
    private String realName;
    /** 模块 */
    private String module;
    /** 响应时间 */
    private Date operationTime;
    /** 业务类型 */
    private BusinessTypeEnum businessType;
    /** 用户id */
    private String userId;
    /** 身份 1 老师 2 学生 */
    private Integer type;

    /** 业务类型枚举转化 */
    public String getBusinessType() {
        return businessType != null ? businessType.getValue() : null;
    }

}
