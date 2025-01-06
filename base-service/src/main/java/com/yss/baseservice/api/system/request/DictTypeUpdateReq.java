package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据字典
 * @author wangqi
 * @date 2021/12/3
 */
@Data
public class DictTypeUpdateReq {

    @NotNull(message = "字典id不能为空")
    private Integer dictId;
    /**
     * 数据字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 数据字典状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
