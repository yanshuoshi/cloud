package com.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录日志
 * @author lijianbin
 * @date 2021-09-18 10:20
 **/
@Data
@AllArgsConstructor
public class LoginLogAddReq {
    /** 登陆人姓名 */
    private String realName;
    /** ip地址 */
    private String ipAddress;
    /** 描述 */
    private String remark;
    /** 登录地址 */
    private String address;
    /** 用户id */
    private String userId;
    /** 身份 1 老师 2 学生 */
    private Integer type;
}
