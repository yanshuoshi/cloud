package com.yss.baseservice.domain.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.base.BaseException;
import com.common.config.SecurityUser;
import com.yss.baseservice.application.system.dto.*;
import com.yss.baseservice.domain.system.entity.Role;
import com.yss.baseservice.domain.system.entity.RoleMenu;
import com.yss.baseservice.domain.system.entity.SysUserRole;
import com.yss.baseservice.domain.system.mapper.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色领域层
 * @author lijianbin
 * @date 2021-09-15 17:32
 **/
@Service
public class RoleDomainService {
    private final RoleMapper roleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final SysUserMapper sysUserMapper;
    private final StudentMapper studentMapper;
    private final RedisTemplate<String,String>  redisTemplate;

    public RoleDomainService(RoleMapper roleMapper, StudentMapper studentMapper,SysUserRoleMapper sysUserRoleMapper, RoleMenuMapper roleMenuMapper, SysUserMapper sysUserMapper, RedisTemplate<String,String> redisTemplate) {
        this.roleMapper = roleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.sysUserMapper = sysUserMapper;
        this.studentMapper = studentMapper;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 查询角色列表
     * @return  角色
     */
    public List<RoleDto> selectRoleList(String roleName, String roleKey, RowBounds rowBounds) {
        return roleMapper.selectRoleList(roleName,roleKey,rowBounds);
    }

    /**
     * 添加角色
     * @param roleName      角色名称
     * @param roleKey       角色标识
     * @param userId        创建者用户id
     */
    public Integer roleSave(String roleName, String roleKey, String userId) {
        Role role = this.roleMapper.selectOne(new QueryWrapper<Role>().eq("role_key",roleKey).eq("status",1));
        if (null!= role){
            throw new BaseException(500,"该角色标识已存在，请勿重复添加");
        }
        role = Role.createRole(roleName, roleKey, userId);
        role.insert();
        return role.getRoleId();
    }

    /**
     * 保存角色
     * @param roleId         角色id
     * @param roleName       角色名称
     * @param roleKey        角色标识
     */
    public void roleUpdate(Integer roleId, String roleName, String roleKey) {
        Role role = Role.updateRole(roleId, roleName, roleKey);
        role.updateById();
    }

    /**
     * 删除角色
     * @param roleId    角色id
     */
    public void roleRemove(Integer roleId) {
        Role role = Role.removeRole(roleId);
        role.updateById();
        //删除角色之后需要删除角色菜单关联关系
        this.roleMenuMapper.deleteByRoleOrMenu(roleId,null);
        //同时需要删除用户跟角色的关联关系
        this.sysUserRoleMapper.deleteByUserOrRole(null,roleId);
    }

    /**
     * 通过用户id查询角色
     * @param   userId    用户id
     * @return  角色
     */
    public List<String> selectRoleByUserId(String userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<SysUserRole> list = sysUserRoleMapper.selectList(queryWrapper);
        //当人员角色不为空时查询相关权限
        if(!list.isEmpty()){
            List<Integer> roleId = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.in("role_id", roleId);
            List<Role> roleList = roleMapper.selectList(roleQueryWrapper);
            return roleList.stream().map(Role::getRoleKey).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 分配角色
     * @param userId 用户id
     * @param roleIds 角色id
     */
    public void roleAssign(String userId,int[] roleIds){
        //修改角色时 需要删除原用户所有角色
        this.sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id",userId));
        if(null !=roleIds && roleIds.length>0){
            List<SysUserRole> list = new ArrayList<>();
            for (int roleId: roleIds) {
                SysUserRole sysUserRole = SysUserRole.createAssociating(userId,roleId);
                list.add(sysUserRole);
            }
            //添加角色
            this.sysUserRoleMapper.insertAll(list);
        }
    }

    /**
     *  保存权限配置
     * @param roleId 角色id
     * @param menuIds 菜单id数组 一个角色可配置多个菜单
     */
    public void saveRoleConfiguration(Integer roleId,Integer[] menuIds){
        //保存菜单权限时需要删原角色所有菜单配置
        this.roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        List<RoleMenu> list = new ArrayList<>();
        for (int menuId: menuIds) {
            RoleMenu roleMenu = RoleMenu.createConfiguration(roleId,menuId);
            list.add(roleMenu);
        }
        this.roleMenuMapper.insertAll(list);

    }


    /**
     * 查询角色列表（分配角色使用）
     * @param userId 用户id 此处用户id为查询该用户已选择的角色，用于前端回显
     * @return  List<RoleCheckDto>
     */
    public List<RoleCheckDto> roleAll(String userId){
        return this.roleMapper.roleAll(userId);
    }


    /**
     * 查询到权限标识
     * @return Map
     */
    public Map<String,Object> findUserRoleConfiguration(){
        //获取登录名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        //查询用户
        SysUserDto sysUser = this.sysUserMapper.getUserByName(username);
        //返回标识和用户信息集合
        Map<String,Object> map =  new HashMap<>(16);
        List<String> keyList = new ArrayList<>();
        //从缓存中获取用户角色
        SecurityUser securityUser = JSONObject.parseObject(this.redisTemplate.opsForValue().get(username),SecurityUser.class);
        if(null != securityUser){
            List<String> roleList = securityUser.getRoleList();
            //通过角色查询缓存中的权限标识,并放入集合中
            if(!roleList.isEmpty()){
                for(String r : roleList){
                    //获取到权限标识集合
                    Set<String> set =  JSON.parseObject(redisTemplate.opsForValue().get("permission"),new TypeReference<Map<String, Set<String>>>() {}).get(r);
                    //遍历Set
                    if(null != set && !set.isEmpty()){
                        Iterator<String> it = set.iterator();
                        it.forEachRemaining(keyList::add);
                    }
                }
            }else{
                throw  new BaseException(500,"权限不足，请联系管理员");
            }
        }
        map.put("user",sysUser);
        map.put("keyList",keyList);
        return map;
    }

    /**
     * 查询学生权限标识
     * @return 权限标识
     */
    public Map<String, Object> findStudentRoleConfiguration() {
        //获取登录名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        //查询用户
        StudentDto student = this.studentMapper.selectDetail(username);
        Map<String,Object> map =  new HashMap<>(16);
        map.put("user",student);
        return map;
    }

    /**
     * 用户登录查询权限
     *
     * @return
     */
    public List<RoleByUserIdDto> getMenuByUserId(String userId) {
        return roleMapper.selectMenuByUserId(userId);
    }
}
