package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yss.baseservice.application.system.dto.RoleNumberPermissionUrlDto;
import com.yss.baseservice.domain.system.entity.RolePermission;
import com.yss.baseservice.domain.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限领域层
 * @author lijianbin
 * @date 2021-09-17 17:05
 **/
@Service
public class RolePermissionDomainService {
    private final RolePermissionMapper rolePermissionMapper;

    public RolePermissionDomainService(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * 关联角色权限
     * @param roleId        角色id
     * @param permissions   权限列表
     */
    public void associatingRolePermission(Integer roleId, List<Integer> permissions) {
        // 删除原关联
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", roleId);
        rolePermissionMapper.delete(queryWrapper);
        // 保存
        List<RolePermission> list = permissions.stream().map(permission ->
                RolePermission.createAssociating(roleId, permission)).collect(Collectors.toList());
        rolePermissionMapper.insertAll(list);
    }

    /**
     * 通过角色id查询权限
     * @param roleId    角色id
     * @return  权限
     */
    public List<Integer> selectPermissionByRoleId(Integer roleId) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper);
        return rolePermissions.stream().map(RolePermission::getPid).collect(Collectors.toList());
    }

    /**
     * 删除权限关联
     * @param permissionId  权限id
     */
    public void removePermission(Integer permissionId) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", permissionId);
        rolePermissionMapper.delete(queryWrapper);
    }

    /**
     * 查询角色权限关联
     * @return  角色权限
     */
    public Map<String, Set<String>> selectPermissionAll() {
        List<RoleNumberPermissionUrlDto> list =  rolePermissionMapper.selectPermissionAll();
        return list.stream().collect(Collectors.groupingBy(RoleNumberPermissionUrlDto::getPermission,
                Collectors.mapping(RoleNumberPermissionUrlDto::getRole, Collectors.toSet())));
    }
}
