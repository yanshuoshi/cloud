package com.yss.baseservice.domain.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.base.BaseException;
import com.yss.baseservice.api.system.request.MenuReq;
import com.yss.baseservice.api.system.request.MenuUpdateReq;
import com.yss.baseservice.application.system.dto.MenuDto;
import com.yss.baseservice.application.system.dto.MenuTreeDto;
import com.yss.baseservice.domain.system.entity.Menu;
import com.yss.baseservice.domain.system.entity.RoleMenu;
import com.yss.baseservice.domain.system.mapper.MenuMapper;
import com.yss.baseservice.domain.system.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单service
 * @author wangqi
 * @date 2021/11/2
 */
@Service
public class MenuDomainService {

    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    public MenuDomainService(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }


    /**
     * 查询菜单列表
     * @param menuName 菜单名称
     * @return List<MenuDto>
     */
    public List<MenuDto> selectMenuList(String menuName){
        return menuMapper.selectMenuList(menuName);
    }

    /**
     * 添加菜单
     * @param req 请求类
     * @param userId 用户id
     */
    public void addMenu(MenuReq req, String userId){
        Menu menu = this.menuMapper.selectOne(new QueryWrapper<Menu>().eq("permission_key",req.getPermissionKey()).eq("status",1));
        if (menu != null) {
            throw new BaseException(500, "该菜单权限标识已存在,不能重复添加");
        }
        //添加菜单
        menu = Menu.createMenu(req.getParentId(),req.getMenuName(),req.getPath(),req.getMenuType(),req.getPermissionKey(),req.getSort(),userId);
        menu.insert();
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    public void deleteMenu(Integer menuId){
        this.menuMapper.deleteMenu(menuId);
        //删除菜单后删除菜单角色关联关系
        this.roleMenuMapper.deleteByMenuId(menuId);
    }

    /**
     * 根据id 查询菜单
     * @param menuId 菜单id
     * @return MenuDto
     */
    public  MenuDto selectMenuById(Integer menuId){
        return this.menuMapper.selectMenuById(menuId);
    }

    /**
     * 修改菜单
     * @param req 修改请求类
     */
    public void updateMenu(MenuUpdateReq req){
        Menu menu = Menu.updateMenu(req.getMenuId(),req.getParentId(),req.getMenuName(),req.getPath(),req.getMenuType(),req.getPermissionKey(),req.getSort());
        menu.updateById();
    }

    /**
     * 根据角色id查询菜单树
     * @param roleId 角色id
     * @return  List<MenuTreeDto>
     */
    public List<MenuTreeDto> selectMenuTree(Integer roleId){
        List<RoleMenu> roleMenuList = new ArrayList<>();
        //根据角色id查询菜单角色表
        if(roleId == null){
             roleMenuList = this.roleMenuMapper.selectList(new QueryWrapper<RoleMenu>());
        }
        roleMenuList = this.roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        //得到该角色菜单集合
        List<Integer>  menuIdList = roleMenuList.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());
        if (menuIdList.isEmpty()){
            return  this.menuMapper.selectMenuTree();
        }else{
            return  this.menuMapper.selectMenuTreeByMenuId(menuIdList);
        }
    }

    /**
     * 选择父级菜单树
     * @return  List<MenuTreeDto>
     */
    public List<MenuTreeDto> menuTreeOfParent(){
        return this.menuMapper.selectMenuTree();
    }

}
