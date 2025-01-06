package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;

/**
 * 角色列表请求类
 * @author wangqi
 * @date 2021/11/3
 */
@Data
public class RoleListReq extends BasePageReq {

    /** 角色名称*/
    private String roleName;
    /**角色标识*/
    private String realKey;
}
