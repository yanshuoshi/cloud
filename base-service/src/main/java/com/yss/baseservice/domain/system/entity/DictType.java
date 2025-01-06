package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.util.Date;

/**
 * 数据字典
 * @author wangqi
 * @date 2021/12/3
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictType extends Model<DictType> {

    /**
     * 主键id
     */
    @TableId
    private Integer  dictId;
    /**
     * 字典名称
     */
    private String  dictName;
    /**
     * 字典类型
     */
    private String  dictType;
    /**
     * 字典状态
     */
    private Integer  status;
    /**
     * 创建人
     */
    private String  createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String  remark;

    /**
     * 新增数据字典
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @param status   字典状态
     * @param remark   备注
     * @param userId   创建用户id
     * @return DictType
     */
    public static DictType createDictType(String  dictName, String dictType,Integer status,String remark,String userId) {
        return DictType.builder().dictName(dictName).dictType(dictType).status(status).remark(remark)
                        .createTime(new Date()).createUserId(userId).build();
    }


    /**
     * 修改数据字典
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @param status   字典状态
     * @param remark   备注
     * @param dictId   主键id
     * @return DictType
     */
    public static DictType updateDictType(Integer dictId ,String  dictName, String dictType,Integer status,String remark) {
        return DictType.builder().dictId(dictId).dictName(dictName).dictType(dictType).status(status)
                .remark(remark).build();
    }

}
