package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织管理列表请求类
 * @author wangqi
 * @date 2021/11/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationListReq extends BasePageReq {

    /**
     * 组织名称
     */
    private String organizationName;

    
}
