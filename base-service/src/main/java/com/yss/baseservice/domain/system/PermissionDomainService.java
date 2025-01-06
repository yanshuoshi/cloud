package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yss.baseservice.application.system.dto.PermissionDto;
import com.yss.baseservice.domain.system.entity.Permission;
import com.yss.baseservice.domain.system.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限领域层
 * @author lijianbin
 * @date 2021-09-18 09:06
 **/
@Service
public class PermissionDomainService {
    private final PermissionMapper permissionMapper;

    public PermissionDomainService(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 查询全部权限
     * @return  权限列表
     */
    public List<PermissionDto> selectPermission() {
        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<>());
        return permissions.stream().map(permission -> new PermissionDto(permission.getId(), permission.getParentId(),
                permission.getName(), permission.getNumber(), permission.getUrl(), permission.getIsMenu(),
                permission.getDescription(), permission.getPriority())).collect(Collectors.toList());
    }

    /**
     * 新增权限
     * @param parentId      父级id
     * @param name          名称
     * @param number        标识
     * @param isMenu        1-菜单;0-目录;
     * @param url           地址
     * @param description   描述
     * @param priority      排序
     */
    public void savePermission(Integer parentId, String name, String number, Integer isMenu, String url,
                               String description, Integer priority) {
        Permission permission = Permission.createPermission(parentId, name, number, isMenu, url, description, priority);
        permission.insert();
    }

    /**
     * 修改权限
     * @param id            主键id
     * @param name          名称
     * @param number        标识
     * @param isMenu        1-菜单;0-目录;
     * @param url           地址
     * @param description   描述
     * @param priority      排序
     */
    public void updatePermission(Integer id, String name, String number, Integer isMenu, String url, String description, Integer priority) {
        Permission permission = Permission.updatePermission(id, name, number, isMenu, url, description, priority);
        permission.updateById();
    }

    /**
     * 删除权限
     * @param id    权限id
     */
    public void removePermission(Integer id) {
        permissionMapper.deleteById(id);
    }
}
