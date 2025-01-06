package com.yss.baseservice.api.system.request;

import lombok.Data;

/**
 * 保存权限配置请求类
 * @author wangqi
 * @date 2021/11/5
 */
@Data
public class RoleConfigurationReq {
    /**
     *  角色id
     */
    private Integer roleId;

    /**
     * 菜单id数组
     */
    private Integer[] menuIds;
}
