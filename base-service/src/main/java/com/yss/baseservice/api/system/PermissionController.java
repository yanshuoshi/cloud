package com.yss.baseservice.api.system;

import com.common.base.Response;
import com.common.utils.Tree;
import com.yss.baseservice.api.system.request.PermissionReq;
import com.yss.baseservice.api.system.request.PermissionUpdateReq;
import com.yss.baseservice.application.system.PermissionApplication;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理
 * @author lijianbin
 * @date 2021-09-17 17:30
 **/
@RestController
@RequestMapping("/system/permission")
public class PermissionController {
    private final PermissionApplication permissionApplication;

    public PermissionController(PermissionApplication permissionApplication) {
        this.permissionApplication = permissionApplication;
    }

    /**
     * 权限树
     */
    @GetMapping("permissionTree")
    public Response permissionTree(){
        List<Tree> list = permissionApplication.permissionTree();
        return Response.ok(list);
    }

    /**
     * 根据角色id查询权限
     */
    @GetMapping("permissionByRid")
    public Response permissionByRoleId(@RequestParam Integer rid){
        List<Integer> list = permissionApplication.permissionByRoleId(rid);
        return Response.ok(list);
    }

    /**
     * 新增权限
     */
    @OperLog(operDesc = "添加路由")
    @PostMapping("permissionSave")
    public Response permissionSave(@RequestBody @Validated PermissionReq req){
        permissionApplication.permissionSave(req);
        return Response.ok();
    }

    /**
     * 修改权限
     */
    @PostMapping("permissionUpdate")
    public Response permissionUpdate(@RequestBody @Validated PermissionUpdateReq req){
        permissionApplication.permissionUpdate(req);
        return Response.ok();
    }

    /**
     * 删除权限
     */
    @OperLog(operDesc = "删除路由")
    @DeleteMapping("permissionRemove")
    public Response permissionRemove(@RequestParam Integer id){
        permissionApplication.permissionRemove(id);
        return Response.ok();
    }
}
