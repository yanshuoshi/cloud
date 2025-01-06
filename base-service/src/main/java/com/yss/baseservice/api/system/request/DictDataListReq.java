package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据列表请求类
 * @author wangqi
 * @date 2021/12/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataListReq extends BasePageReq {

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 状态
     */
    private Integer status;


}
