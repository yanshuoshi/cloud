package com.yss.baseservice.domain.system.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * 操作日志
 * @author
 * @date 2021-09-18 10:25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sys_operation_log")
public class OperationLog {

    private String logId;
    /** 模块 */
    @Field
    private String module;
    /** 业务类型 */
    @Field
    private String businessType;
    /** 操作员账号 */
    @Field
    private String realName;
    /** 响应时间 */
    @Field
    private Date operationTime;
    /** 1 老师操作 2 学生操作 */
    @Field
    private Integer type;
    /** 用户id */
    @Field
    private String userId;


    /**
     * 保存操作日志
     * @param module       模块
     * @param businessType 业务类型
     */
    public static OperationLog createOperationLog(String module, String businessType, String realName,Integer type,String userId) {
        return OperationLog.builder().module(module).businessType(businessType).realName(realName).type(type).userId(userId).operationTime(new Date()).build();
    }
}
