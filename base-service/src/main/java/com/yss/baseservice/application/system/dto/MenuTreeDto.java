package com.yss.baseservice.application.system.dto;

import com.common.utils.Tree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 菜单树包装类
 * @author wnagqi
 * @date 2021/11/3
 */
@Data
@NoArgsConstructor
public class MenuTreeDto implements Tree {

    /**
     * 节点id
     */
    private Integer id;

    /**
     * 父节点id
     */
    private Integer parentId;

    /**
     * 节点名称
     */
    private String menuName;

    /**
     * 是否展开
     */
    private Boolean open;
    /**
     * 是否被选中
     */
    private Boolean checked;

    /**
     * 子菜单
     */
    private List<Tree> children;


    public static MenuTreeDto createParent(List<Tree> children) {
        MenuTreeDto menuTreeDto = new MenuTreeDto();
        menuTreeDto.setChecked(true);
        menuTreeDto.setId(0);
        menuTreeDto.setMenuName("主目录");
        menuTreeDto.setOpen(true);
        menuTreeDto.setParentId(0);
        menuTreeDto.setChildren(children);
        return menuTreeDto;
    }




}
