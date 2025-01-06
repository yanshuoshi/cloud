package com.yss.baseservice.domain.system;

import com.yss.baseservice.application.system.dto.RoleNumberPermissionUrlDto;
import com.yss.baseservice.domain.system.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单角色service
 * @author wangqi
 * @date 2021/11/3
 */
@Service
public class RoleMenuDomainService {

    private final RoleMenuMapper roleMenuMapper;

    public RoleMenuDomainService(RoleMenuMapper roleMenuMapper) {
        this.roleMenuMapper = roleMenuMapper;
    }


    /**
     * 查询角色权限关联
     * @return  角色权限
     */
    public Map<String, Set<String>> selectPermissionAll() {
        List<RoleNumberPermissionUrlDto> list =  roleMenuMapper.selectPermissionAll();
        return list.stream().collect(Collectors.groupingBy(RoleNumberPermissionUrlDto::getRole,
                Collectors.mapping(RoleNumberPermissionUrlDto::getPermission, Collectors.toSet())));
    }



}
