package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 登录日志
 * @author lijianbin
 * @date 2021-09-18 10:20
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
@Builder
@Data
public class LoginLog extends Model<LoginLog> {
    @TableId
    private Integer logId;
    /** 登陆人姓名 */
    private String realName;
    /** ip地址 */
    private String ipAddress;
    /** 创建时间 */
    private Date loginTime;
    /** 描述 */
    private String remark;
    /** 登录地址 */
    private String address;
    /** 用户id */
    private String userId;
    /** 身份 1 老师 2 学生 */
    private Integer type;

    /**
     * 保存操作日志
     * @param remark      备注
     * @param ipAddress   ip地址
     */
    public static LoginLog createLoginLog(String realName, String ipAddress,String remark,String address,String userId,Integer type) {
        return LoginLog.builder().realName(realName).ipAddress(ipAddress).remark(remark)
                .address(address).userId(userId).type(type).loginTime(new Date()).build();
    }
}
