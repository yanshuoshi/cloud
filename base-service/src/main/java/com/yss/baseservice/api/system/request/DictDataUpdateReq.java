package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 字典数据修改请求类
 * @author wangqi
 * @date 2021/12/3
 */
@Data
public class DictDataUpdateReq {

    /**
     * 主键id
     */
    private Integer dictDataId;
    /**
     * 类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;
    /**
     * 标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;
    /**
     * 键值
     */
    @NotBlank(message = "字典键值不能为空")
    private String dictValue;
    /**
     * 排序
     */
    private Integer dictSort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
