package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.github.pagehelper.PageInfo;
import com.yss.baseservice.api.system.request.*;
import com.yss.baseservice.application.system.RoleApplication;
import com.yss.baseservice.application.system.dto.RoleByUserIdDto;
import com.yss.baseservice.application.system.dto.RoleCheckDto;
import com.yss.baseservice.application.system.dto.RoleDto;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author lijianbin
 * @date 2021-09-17 15:40
 **/
@RestController
@RequestMapping("/system/role/")
public class RoleController extends BaseController {
    private final RoleApplication roleApplication;

    public RoleController(RoleApplication roleApplication) {
        this.roleApplication = roleApplication;
    }

    /**
     * 角色列表
     */
    @GetMapping("roleList")
    public Response roleList(@Validated RoleListReq roleListReq) {
        List<RoleDto> list = roleApplication.roleList(roleListReq.getRoleName(),roleListReq.getRealKey(),roleListReq.buildRowBounds());
        return Response.ok(new PageInfo<>(list));
    }

    /**
     * 添加角色
     */
    @PostMapping("roleSave")
    @OperLog(operModule = "角色管理",operType = "CREATE")
    public Response roleSave(@RequestBody @Validated RoleReq req){
        Integer roleId = roleApplication.roleSave(req, this.getUserId());
        return Response.ok(roleId);
    }

    /**
     * 修改角色
     */
    @PostMapping("roleUpdate")
    @OperLog(operModule = "角色管理",operType = "UPDATE")
    public Response roleUpdate(@RequestBody @Validated RoleUpdateReq req){
        roleApplication.roleUpdate(req);
        return Response.ok();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("roleRemove")
    @OperLog(operModule = "角色管理",operType = "DELETE")
    public Response roleRemove(@RequestParam Integer roleId){
        roleApplication.roleRemove(roleId);
        return Response.ok();
    }

    /**
     * 分配角色
     * @param req 分配角色请求类
     * @return Response
     */
    @PostMapping("assignRoles")
    @OperLog(operModule = "用户管理-分配角色",operType = "CREATE")
    public Response assignRoles(@RequestBody @Validated RoleAssignReq req){
        roleApplication.roleAssign(req.getUserId(),req.getRoleIds());
        return Response.ok();
    }

    /**
     * 保存权限配置
     * @param req 保存配置请求类
     * @return Response
     */
    @PostMapping ("saveRoleConfiguration")
    @OperLog(operModule = "角色管理-分配权限",operType = "CREATE")
    public Response saveRoleConfiguration(@RequestBody @Validated RoleConfigurationReq req){
        roleApplication.saveRoleConfiguration(req.getRoleId(),req.getMenuIds());
        return Response.ok();
    }

    /**
     * 获取角色列表（添加用户时下拉列表使用）
     * @return 角色列表
     */
    @GetMapping("roleAll")
    public Response roleAll(@RequestParam String userId){
        List<RoleCheckDto> roleDtoList = roleApplication.roleAll(userId);
        return Response.ok(roleDtoList);
    }


    /**
     *  查询到该用户权限配置信息
     * @return map 包含用户信息和权限
     */
    @GetMapping("findUserRoleConfiguration")
    public Response findUserRoleConfiguration(){
        Map<String,Object> map = this.roleApplication.findUserRoleConfiguration();
        return Response.ok(map);
    }
    /**
     *  查询学生用户权限配置信息
     * @return map 包含用户信息和权限
     */
    @GetMapping("findStudentRoleConfiguration")
    public Response findStudentRoleConfiguration(){
        Map<String,Object> map = this.roleApplication.findStudentRoleConfiguration();
        return Response.ok(map);
    }

    /**
     * 用户登录查询权限
     *
     * @return
     */
    @GetMapping("/getRoleByUserId")
    public Response<List<RoleByUserIdDto>> getMenuByUserId(@RequestParam String userId){
        List<RoleByUserIdDto> list = roleApplication.getMenuByUserId(userId);
        return Response.ok(list);
    }



}
