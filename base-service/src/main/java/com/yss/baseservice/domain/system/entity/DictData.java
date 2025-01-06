package com.yss.baseservice.domain.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.util.Date;

/**
 * 字典数据
 * @author wanngqi
 * @date 2021/12/3
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictData extends Model<DictData> {

    /**
     * 主键id
     */
    @TableId
    private Integer dictDataId;

    /**
     * 排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典键值
     */
    private String dictValue;
    /**
     * 字典键值
     */
    private String dictType;
    /**
     * 字典键值状态
     */
    private Integer status;
    /**
     * 用户id
     */
    private String createUserId;
    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 新增字典数据
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param dictValue 字典键值
     * @param dictSort 字典排序
     * @param status 状态
     * @param remark 备注
     * @param userId 用户id
     * @return DictData
     */
    public static DictData createDictData(String  dictType, String dictLabel, String dictValue,Integer dictSort,Integer status,String remark,String userId) {
        return DictData.builder().dictType(dictType).dictLabel(dictLabel).dictValue(dictValue).dictSort(dictSort).status(status).remark(remark)
                .createTime(new Date()).createUserId(userId).build();
    }

    /**
     * 修改字典数据
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param dictValue 字典键值
     * @param dictSort 字典排序
     * @param status 状态
     * @param remark 备注
     * @param dictDataId 主键id
     * @return DictData
     */
    public static DictData updateDictData(Integer dictDataId ,String  dictType, String dictLabel, String dictValue,Integer dictSort,Integer status,String remark) {
        return DictData.builder().dictDataId(dictDataId).dictType(dictType).dictLabel(dictLabel).dictValue(dictValue).dictSort(dictSort).status(status)
                .remark(remark).build();
    }
}
