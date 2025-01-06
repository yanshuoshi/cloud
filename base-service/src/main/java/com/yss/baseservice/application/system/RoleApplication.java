package com.yss.baseservice.application.system;

import com.alibaba.fastjson.JSON;
import com.yss.baseservice.api.system.request.RoleReq;
import com.yss.baseservice.api.system.request.RoleUpdateReq;
import com.yss.baseservice.application.system.dto.RoleByUserIdDto;
import com.yss.baseservice.application.system.dto.RoleCheckDto;
import com.yss.baseservice.application.system.dto.RoleDto;
import com.yss.baseservice.domain.system.RoleDomainService;
import com.yss.baseservice.domain.system.RoleMenuDomainService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色应用层
 * @author lijianbin
 * @date 2021-09-17 16:25
 **/
@Component
@Transactional(rollbackFor = Throwable.class)
public class RoleApplication {
    private final RoleDomainService roleDomainService;
    private final RoleMenuDomainService roleMenuDomainService;
    private final RedisTemplate<String, String> redisTemplate;

    public RoleApplication(RoleDomainService roleDomainService, RoleMenuDomainService roleMenuDomainService, RedisTemplate<String, String> redisTemplate) {
        this.roleDomainService = roleDomainService;
        this.roleMenuDomainService = roleMenuDomainService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询角色列表
     * @return  角色
     */
    public List<RoleDto> roleList(String roleName, String roleKey, RowBounds rowBounds) {
        return roleDomainService.selectRoleList(roleName,roleKey,rowBounds);
    }

    /**
     * 新增角色
     * @param req       角色信息
     * @param userId    创建者用户id
     */
    public Integer roleSave(RoleReq req, String userId) {
        // 保存角色
       return roleDomainService.roleSave(req.getRoleName(), req.getRoleKey(), userId);
    }

    /**
     * 修改角色
     * @param req   角色信息
     */
    public void roleUpdate(RoleUpdateReq req) {
        // 保存角色
        roleDomainService.roleUpdate(req.getRoleId(), req.getRoleName(), req.getRoleKey());
    }

    /**
     * 删除角色
     * @param id    角色id
     */
    public void roleRemove(Integer id) {
        roleDomainService.roleRemove(id);
        // 缓存权限信息
        Map<String, Set<String>> permissionMap = roleMenuDomainService.selectPermissionAll();
        redisTemplate.opsForValue().set("permission", JSON.toJSONString(permissionMap));
    }

    /**
     * 分配角色
     * @param userId 用户id
     * @param roleIds 角色id数组
     */
    public void roleAssign(String userId,int[] roleIds){
        roleDomainService.roleAssign(userId,roleIds);
    }

    /**
     * 保存权限配置
     * @param roleId 角色id
     * @param menuIds 菜单id数组
     */
    public void saveRoleConfiguration(Integer roleId, Integer[] menuIds){
        roleDomainService.saveRoleConfiguration(roleId,menuIds);
        // 缓存权限信息
        Map<String, Set<String>> permissionMap = roleMenuDomainService.selectPermissionAll();
        redisTemplate.opsForValue().set("permission", JSON.toJSONString(permissionMap));
    }

    /**
     * 查询角色列表（分配角色使用）
     * @param userId 用户id 此处用户id为查询该用户已选择的角色，用于前端回显
     * @return 角色列表
     */
    public List<RoleCheckDto> roleAll(String userId){
        return roleDomainService.roleAll(userId);
    }


    /**
     * 查询用户权限标识
     * @return 权限标识
     */
    public Map<String,Object> findUserRoleConfiguration(){
        return this.roleDomainService.findUserRoleConfiguration();
    }

    /**
     * 查询学生权限标识
     * @return 权限标识
     */
    public Map<String,Object> findStudentRoleConfiguration(){
        return this.roleDomainService.findStudentRoleConfiguration();
    }


    /**
     * 用户登录查询权限
     *
     * @return
     */
    public List<RoleByUserIdDto> getMenuByUserId(String userId) {
        return roleDomainService.getMenuByUserId(userId);
    }
}
