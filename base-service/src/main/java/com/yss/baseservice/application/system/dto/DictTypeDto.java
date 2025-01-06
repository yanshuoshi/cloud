package com.yss.baseservice.application.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * 数据字典返回包装类
 * @author wangqi
 * @date 2021/12/3
 */
@Data
public class DictTypeDto {

    /**
     * 字典id
     */
    private Integer dictId;

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
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;




}
