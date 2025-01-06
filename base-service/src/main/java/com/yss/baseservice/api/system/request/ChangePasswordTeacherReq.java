package com.yss.baseservice.api.system.request;

import lombok.Data;

/**
 * 修改考官密码
 * @author wangqi
 * @date 2021/12/17
 */
@Data
public class ChangePasswordTeacherReq {
    /**
     * 考官id
     */
    private String teacherId;

    /**
     * 老密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
}
