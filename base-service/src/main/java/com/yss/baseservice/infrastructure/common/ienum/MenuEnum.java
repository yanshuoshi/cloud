package com.yss.baseservice.infrastructure.common.ienum;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 菜单枚举类
 * @author wangqi
 * @date 2022/1/5
 */
public enum MenuEnum implements IEnum<String> {

    /** 菜单 */
    C("菜单"),
    /** 按钮 */
    F("按钮"),
    /** 目录 */
    M("目录");

    private final String value;

    MenuEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }


    public static MenuEnum getValue(String key) {
        for (MenuEnum menu : MenuEnum.values()) {
            if (menu.value.equals(key)) {
                return menu;
            }
        }
        return null;
    }
}
