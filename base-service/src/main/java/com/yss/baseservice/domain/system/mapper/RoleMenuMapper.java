package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.RoleNumberPermissionUrlDto;
import com.yss.baseservice.domain.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单角色mapper
 * @author wangqi
 * @date 2021/11/1
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    /**
     * 查询角色权限关联
     * @return  角色权限
     */
    List<RoleNumberPermissionUrlDto> selectPermissionAll();

    /**
     * 批量保存权限配置
     * @param list 角色菜单集合
     */
    void insertAll(@Param("list") List<RoleMenu> list);


    /**
     * 删除角色菜单关联关系
     * @param roleId 角色id
     * @param menuId 菜单id
     */
    void deleteByRoleOrMenu(@Param("roleId") Integer roleId,@Param("menuId") Integer menuId);

    /**
     * 删除角色菜单关联关系（包含下级）
     * @param menuId 菜单
     */
    void deleteByMenuId(@Param("menuId") Integer menuId);
}
