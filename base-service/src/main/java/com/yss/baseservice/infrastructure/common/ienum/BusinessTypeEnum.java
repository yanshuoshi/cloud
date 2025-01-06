package com.yss.baseservice.infrastructure.common.ienum;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 日志类别
 * @author wangqi
 * @date 2021/11/26
 */
public enum BusinessTypeEnum implements IEnum<String> {

    /** 新增*/
    CREATE("新增"),
    /** 修改 */
    UPDATE("修改"),
    /** 删除 */
    DELETE("删除"),
    /** 查询 */
    SELECT("查询");


    private final String value;

    BusinessTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static BusinessTypeEnum getValue(String key) {
        for (BusinessTypeEnum businessType : BusinessTypeEnum.values()) {
            if (businessType.name().equals(key)) {
                return businessType;
            }
        }
        return null;
    }
}
