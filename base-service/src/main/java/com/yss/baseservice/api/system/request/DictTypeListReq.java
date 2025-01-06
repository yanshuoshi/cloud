package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据字典列表请求类
 * @author wangqi
 * @date 2021/12/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeListReq extends BasePageReq {

    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典状态
     */
    private Integer status;
}
