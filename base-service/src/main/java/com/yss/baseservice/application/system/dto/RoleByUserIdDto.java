package com.yss.baseservice.application.system.dto;

import com.yss.baseservice.infrastructure.common.ienum.MenuEnum;
import lombok.*;

/**
 * 菜单实体类
 * @author
 * @date 2021/11/1
 */
@Data
public class RoleByUserIdDto {

    private Integer roleId;
    /** 名称 */
    private String roleKey;

}
