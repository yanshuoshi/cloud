package com.yss.baseservice.application.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * 登录日志
 * @author lijianbin
 * @date 2021-09-18 11:32
 **/
@Data
public class LoginLogDto {
    /** 主键id */
    private Integer logId;
    /** 登录名(用户名) */
    private String realName;
    /** ip地址 */
    private String ipAddress;
    /** 操作时间 */
    private Date loginTime;
    /** 备注 */
    private String remark;
    /** 登录地址 */
    private String address;
    /** 用户id */
    private String userId;
    /** 身份 1 老师 2 学生 */
    private Integer type;



}
