package com.yss.baseservice.application.system.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户返回包装类
 * @author wangqi
 * @date 2021/11/1
 */
@Data
public class SysUserDto {
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
     * 性别
     */
    private String sexText;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 头像:相对地址
     */
    private String headurl;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
     * 组织机构名称
     */
    private String organizationName;

    /**
     * 专业名称
     */
    private String majorName;

    public String getSexText() {
        if(sex != null){
            return sex.getValue();
        }
        return null;
    }


}
