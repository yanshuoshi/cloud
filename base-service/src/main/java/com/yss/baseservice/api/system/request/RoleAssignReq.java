package com.yss.baseservice.api.system.request;

import lombok.Data;

/**
 * 角色分配请求类
 * @author wangqi
 * @date 2021/11/2
 */
@Data
public class RoleAssignReq {
    /**
     *  用户id
     */
     private String userId;
    /**
     * 角色id 数组
     */
     private int[] roleIds;

}
