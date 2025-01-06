package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.MenuDto;
import com.yss.baseservice.application.system.dto.MenuTreeDto;
import com.yss.baseservice.domain.system.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 菜单mapper
 * @author wangqi
 * @date 2021/11/2
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询菜单列表（树形结构不分页）
     * @param menuName 菜单名称
     * @return
     */
    List<MenuDto> selectMenuList(@Param("menuName") String menuName);

    /**
     * 根据菜单id查询菜单
     * @param menuId
     * @return
     */
    MenuDto selectMenuById(@Param("menuId") Integer menuId);

    /**
     * 查询菜单树
     * @return
     */
    List<MenuTreeDto>  selectMenuTree();

    /**
     * 根据菜单id查询菜单树
     * @param menuIdList 菜单id集合
     * @return
     */
    List<MenuTreeDto>  selectMenuTreeByMenuId(@Param("menuIdList") List<Integer> menuIdList);

    /**
     * 删除菜单（如果包含下级，则删除下级）
     * @param menuId 菜单id
     */
    void deleteMenu(@Param("menuId") Integer menuId);
}
