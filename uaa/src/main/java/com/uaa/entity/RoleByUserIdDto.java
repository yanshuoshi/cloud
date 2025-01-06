package com.uaa.entity;

import com.uaa.config.enums.MenuEnum;
import lombok.Data;

/**
 * 菜单实体类
 * @author
 * @date 2021/11/1
 */
@Data
public class RoleByUserIdDto {

    /** 角色id */
    private Integer roleId;
    /** 角色标识 */
    private String roleKey;

}
