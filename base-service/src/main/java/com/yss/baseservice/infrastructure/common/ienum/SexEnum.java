package com.yss.baseservice.infrastructure.common.ienum;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 性别
 * @author lijianbin
 * @date 2021-09-15 16:24
 **/
public enum SexEnum implements IEnum<String> {
    /** 女 */
    F("女"),
    /** 男 */
    M("男");

    private final String value;

    SexEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static SexEnum getValue(String key) {
        for (SexEnum sex : SexEnum.values()) {
            if (sex.value.equals(key)) {
                return sex;
            }
        }
        return null;
    }
}
