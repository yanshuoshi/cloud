package com.uaa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.uaa.config.enums.SexEnum;
import lombok.*;

import java.util.Date;

/**
 * 用户
 * @author wangqi
 * @date 2021/11/1
 */
@Data
public class SysUser {
    /** 主键 */
    private String userId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别:F-女;M-男
     */
    private SexEnum sex;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 头像:相对地址
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String headurl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     *  创建用户id
     */
    private String createUser;
    /**
     *  状态:1-正常;0-删除
     */
    private Integer status;

    /**
     * 组织id
     */
    private Integer organizationId;

}
