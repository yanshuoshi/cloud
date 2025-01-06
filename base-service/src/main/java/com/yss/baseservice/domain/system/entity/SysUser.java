package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * 用户
 * @author wangqi
 * @date 2021/11/1
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysUser extends Model<SysUser> {
    /** 主键 */
    @TableId(type = IdType.INPUT)
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





    /**
     * 创建用户
     * @param realName 真实姓名
     * @param username 账号
     * @param password 密码
     * @param sex  性别
     * @param mobile 联系方式
     * @param headurl 头像
     * @param organizationId 组织id
     * @param userId 当前用户id
     * @return
     */
    public static SysUser createSysUser(String realName,String username, String password,SexEnum sex, String mobile,
                                        String headurl, Integer organizationId, String userId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return SysUser.builder().userId(IdWorker.getIdStr()).realName(realName).username(username).password(encoder.encode(password))
                .sex(sex).mobile(mobile).headurl(headurl).organizationId(organizationId)
                .createTime(new Date()).updateTime(null).createUser(userId).status(1).build();
    }


    /**
     * 修改系统用户
     * @param userId 用户id
     * @param realName 真实姓名
     * @param sex 性别
     * @param mobile 联系方式
     * @param headurl 头像
     * @param organizationId 组织id
     * @return
     */
    public static SysUser updateSysUser(String userId, String realName, SexEnum sex, String mobile, String headurl, Integer organizationId ) {
        return SysUser.builder().userId(userId).realName(realName).sex(sex).mobile(mobile).headurl(headurl).organizationId(organizationId).build();
    }


    /**
     * 删除系统用户
     * @param userId 用户id
     * @return
     */
    public static SysUser deleteSysUser(String userId) {
        return SysUser.builder().userId(userId).status(0).build();
    }


    /**
     * 重置用户密码
     * @param userId    用户id
     * @param password  密码
     */
    public static SysUser passwordReset(String userId, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return SysUser.builder().userId(userId).password(encoder.encode(password)).build();
    }




}
