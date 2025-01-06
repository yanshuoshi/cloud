package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 数据字典添加请求类
 * @author wangqi
 * @date 2021/12/3
 */
@Data
public class DictTypeReq {

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;


    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 字典类型
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
