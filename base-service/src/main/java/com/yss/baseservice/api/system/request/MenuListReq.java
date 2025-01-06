package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单列表请求类
 * @author wangqi
 * @date 2021/11/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuListReq extends BasePageReq {
    /**
     * 菜单名称
     */
    private String menuName;
}
