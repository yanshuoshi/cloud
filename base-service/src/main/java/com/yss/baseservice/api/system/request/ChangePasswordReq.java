package com.yss.baseservice.api.system.request;

import lombok.Data;

/**
 * 修改密码请求类
 * @author wangqi
 * @date 2021/11/18
 */
@Data
public class ChangePasswordReq {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 老密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
}
