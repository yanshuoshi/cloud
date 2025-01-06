package com.yss.baseservice.application.system;

import com.common.utils.Tree;
import com.common.utils.TreeUtils;
import com.yss.baseservice.api.system.request.PermissionReq;
import com.yss.baseservice.api.system.request.PermissionUpdateReq;
import com.yss.baseservice.application.system.dto.PermissionDto;
import com.yss.baseservice.domain.system.PermissionDomainService;
import com.yss.baseservice.domain.system.RolePermissionDomainService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限应用层
 * @author lijianbin
 * @date 2021-09-18 09:05
 **/
@Component
public class PermissionApplication {
    private final PermissionDomainService permissionDomainService;
    private final RolePermissionDomainService rolePermissionDomainService;

    public PermissionApplication(PermissionDomainService permissionDomainService, RolePermissionDomainService rolePermissionDomainService) {
        this.permissionDomainService = permissionDomainService;
        this.rolePermissionDomainService = rolePermissionDomainService;
    }

    /**
     * 权限树
     * @return  权限
     */
    public List<Tree> permissionTree() {
        List<PermissionDto> list = permissionDomainService.selectPermission();
        return TreeUtils.getChildPerms(list, 0);
    }

    /**
     * 根据角色id查询权限
     * @param roleId    角色id
     * @return  权限
     */
    public List<Integer> permissionByRoleId(Integer roleId) {
        return rolePermissionDomainService.selectPermissionByRoleId(roleId);
    }

    /**
     * 新增权限
     * @param req   权限
     */
    public void permissionSave(PermissionReq req) {
        permissionDomainService.savePermission(req.getParentId(), req.getName(), req.getNumber(), req.getIsMenu(),
                req.getUrl(), req.getDescription(), req.getPriority());
    }

    /**
     * 修改权限
     * @param req   权限
     */
    public void permissionUpdate(PermissionUpdateReq req) {
        permissionDomainService.updatePermission(req.getId(), req.getName(), req.getNumber(), req.getIsMenu(),
                req.getUrl(), req.getDescription(), req.getPriority());
    }

    /**
     * 删除权限
     * @param id    权限id
     */
    public void permissionRemove(Integer id) {
        // 删除权限
        permissionDomainService.removePermission(id);
        // 删除角色权限关联
        rolePermissionDomainService.removePermission(id);
    }

    /**
     * 查询所有角色权限
     * @return  权限对应角色Map<权限,List<角色>>
     */
    public Map<String, Set<String>> selectPermissionAll() {
        return rolePermissionDomainService.selectPermissionAll();
    }
}
