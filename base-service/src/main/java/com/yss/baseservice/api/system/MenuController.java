package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.common.utils.Tree;
import com.yss.baseservice.api.system.request.MenuListReq;
import com.yss.baseservice.api.system.request.MenuReq;
import com.yss.baseservice.api.system.request.MenuUpdateReq;
import com.yss.baseservice.application.system.MenuApplication;
import com.yss.baseservice.application.system.dto.MenuDto;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 * @author wangqi
 * @date 2021/11/1
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    private final MenuApplication menuApplication;


    public MenuController(MenuApplication menuApplication) {
        this.menuApplication = menuApplication;
    }

    /**
     * 菜单列表
     * @param req 列表请求类
     */
    @GetMapping("/menuList")
    public Response menuList(@Validated MenuListReq req) {
        List<Tree> list = menuApplication.selectMenuList(req.getMenuName());
        return Response.ok(list);
    }


    /**
     * 添加菜单
     * @param req 请求类
     */
    @PostMapping("/addMenu")
    @OperLog(operModule = "菜单管理",operType = "CREATE")
    public Response addMenu(@RequestBody @Validated MenuReq req) {
        menuApplication.addMenu(req, this.getUserId());
        return Response.ok();
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    @DeleteMapping("/deleteMenu")
    @OperLog(operModule = "菜单管理",operType = "DELETE")
    public Response deleteMenu(@RequestParam Integer menuId) {
        menuApplication.deleteMenu(menuId);
        return Response.ok();
    }



    /**
     * 根据id查询菜单信息
     * @param menuId 菜单id
     */
    @GetMapping ("/selectMenuById")
    public Response selectMenuById(@RequestParam Integer menuId) {
        MenuDto menuDto = menuApplication.selectMenuById(menuId);
        return Response.ok(menuDto);
    }

    /**
     * 修改菜单
     * @param req 修改请求类
     */
    @PostMapping("/updateMenu")
    @OperLog(operModule = "菜单管理",operType = "UPDATE")
    public Response updateMenu(@RequestBody @Validated MenuUpdateReq req) {
        menuApplication.updateMenu(req);
        return Response.ok();
    }


    /**
     * 菜单树(权限分配使用)
     * @param roleId 角色id
     */
    @GetMapping("/getMenuTree")
    public Response getMenuTree( Integer roleId){
        List<Tree> list = menuApplication.menuTree(roleId);
        return Response.ok(list);
    }

    /**
     * 选择父级菜单使用
     */
    @GetMapping("/getMenuTreeOfParent")
    public Response getMenuTreeOfParent(){
        List<Tree> list = menuApplication.menuTreeOfParent();
        return Response.ok(list);
    }


    @GetMapping("/getMenu")
    public Response getMenuTreeOfLeft(){
        List<Tree> list = menuApplication.menuTreeOfParent();
        return Response.ok(list);
    }


}
