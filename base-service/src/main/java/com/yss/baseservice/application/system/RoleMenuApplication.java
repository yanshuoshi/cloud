package com.yss.baseservice.application.system;

import com.yss.baseservice.domain.system.RoleMenuDomainService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


/**
 * 菜单权限
 * @author wangqi
 * @date 2021/11/3
 */
@Component
public class RoleMenuApplication {

    private final RoleMenuDomainService roleMenuDomainService;

    public RoleMenuApplication(RoleMenuDomainService roleMenuDomainService) {
        this.roleMenuDomainService = roleMenuDomainService;
    }

    /**
     * 查询所有角色权限
     * @return  权限对应角色Map<权限,List<角色>>
     */
    public Map<String, Set<String>> selectPermissionAll() {
        return roleMenuDomainService.selectPermissionAll();
    }

}
