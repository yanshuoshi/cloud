package com.yss.baseservice.application.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * 字典数据返回包装类
 * @author wangqi
 * @date 2021/12/3
 */
@Data
public class DictDataDto {

    /**
     * 主键id
     */
    private Integer dictDataId;

    /**
     * 排序
     */
    private Integer dictSort;
    /**
     * 标签
     */
    private String dictLabel;

    /**
     * 键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
}
