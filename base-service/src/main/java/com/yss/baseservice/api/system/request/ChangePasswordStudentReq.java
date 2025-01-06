package com.yss.baseservice.api.system.request;

import lombok.Data;

/**
 * 学生密码修改请求类
 * @author wangqi
 * @date 2021/12/17
 */
@Data
public class ChangePasswordStudentReq {
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 老密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
}
