package com.yss.baseservice.application.system;

import com.alibaba.fastjson.JSON;
import com.common.utils.Tree;
import com.common.utils.TreeUtils;
import com.yss.baseservice.api.system.request.MenuReq;
import com.yss.baseservice.api.system.request.MenuUpdateReq;
import com.yss.baseservice.application.system.dto.MenuDto;
import com.yss.baseservice.application.system.dto.MenuTreeDto;
import com.yss.baseservice.domain.system.MenuDomainService;
import com.yss.baseservice.domain.system.RoleMenuDomainService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *  菜单应用层
 * @author wangqi
 * @date 2021/11/3
 */
@Component
@Transactional(rollbackFor = Throwable.class)
public class MenuApplication {

    private final MenuDomainService menuDomainService;
    private final RoleMenuDomainService roleMenuDomainService;
    private final RedisTemplate<String,String> redisTemplate;

    public MenuApplication(MenuDomainService menuDomainService, RoleMenuDomainService roleMenuDomainService, RedisTemplate<String, String> redisTemplate) {
        this.menuDomainService = menuDomainService;
        this.roleMenuDomainService = roleMenuDomainService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 菜单列表
     * @param menuName 菜单名称
     * @return 菜单列表
     */
    public List<Tree> selectMenuList(String menuName){
        List<MenuDto> list = menuDomainService.selectMenuList(menuName);
        if(list.isEmpty()){
           return TreeUtils.getChildPerms(list, 0);
        }
        return  TreeUtils.getChildPerms(list, list.get(0).getParentId());
    }

    /**
     * 添加菜单
     * @param req 请求类
     * @param userId 用户id
     */
    public void addMenu(MenuReq req, String userId){
        menuDomainService.addMenu(req,userId);
        // 缓存权限信息
        Map<String, Set<String>> permissionMap = roleMenuDomainService.selectPermissionAll();
        redisTemplate.opsForValue().set("permission", JSON.toJSONString(permissionMap));
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    public void deleteMenu(Integer menuId){
        menuDomainService.deleteMenu(menuId);
        // 缓存权限信息
        Map<String, Set<String>> permissionMap = roleMenuDomainService.selectPermissionAll();
        redisTemplate.opsForValue().set("permission", JSON.toJSONString(permissionMap));
    }

    /**
     * 根据ic查询菜单信息
     * @param menuId 菜单id
     * @return 菜单信息
     */
    public MenuDto selectMenuById(Integer menuId){
       return menuDomainService.selectMenuById(menuId);
    }

    /**
     * 修改菜单
     * @param req 修改菜单请求类
     */
    public void updateMenu(MenuUpdateReq req){
        this.menuDomainService.updateMenu(req);
        // 缓存权限信息
        Map<String, Set<String>> permissionMap = roleMenuDomainService.selectPermissionAll();
        redisTemplate.opsForValue().set("permission", JSON.toJSONString(permissionMap));
    }


    /**
     * 查询菜单树（配置权限适用）
     * @param roleId 角色id
     * @return 树形菜单
     */
    public List<Tree> menuTree(Integer roleId){
        List<MenuTreeDto> list = menuDomainService.selectMenuTree(roleId);
        return TreeUtils.getChildPerms(list, 0);
    }

    /**
     * 查询菜单树（选择父级菜单使用）
     * @return 弹窗的菜单树
     */
    public List<Tree> menuTreeOfParent(){
        List<MenuTreeDto> list = menuDomainService.menuTreeOfParent();
        List<Tree>  treeList = new ArrayList<>();
        //包装主目录
        treeList.add(MenuTreeDto.createParent(TreeUtils.getChildPerms(list, 0)));
        return treeList;
    }

}
