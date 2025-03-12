package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.github.pagehelper.PageInfo;
import com.yss.baseservice.api.system.request.ChangePasswordReq;
import com.yss.baseservice.api.system.request.SysUserListReq;
import com.yss.baseservice.api.system.request.SysUserReq;
import com.yss.baseservice.api.system.request.SysUserUpdateReq;
import com.yss.baseservice.application.system.SysUserApplication;
import com.yss.baseservice.application.system.dto.SysUserDto;
import com.yss.baseservice.domain.system.entity.SysUser;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统用户
 *
 * @author wangqi
 * @date 2021/11/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/sysUser")
public class SysUserController extends BaseController {

    private final SysUserApplication sysUserApplication;


    /**
     * 系统用户分页列表
     *
     * @param req 用户请求类
     * @return 用户列表
     */
    @GetMapping("/userList")
    @OperLog(operModule = "用户管理", operType = "SELECT")
    public Response userList(@Validated SysUserListReq req) {
        List<SysUserDto> list = sysUserApplication.selectSysUserList(req.getUsername(), req.getRealName(), req.getMobile(), req.getSex(), req.getOrganizationId(), req.buildRowBounds());
        return Response.ok(new PageInfo<>(list));
    }


    /**
     * 添加系统用户
     *
     * @param req 用户请求类
     * @return Response
     */
    @PostMapping("/addSysUser")
    @OperLog(operModule = "用户管理", operType = "CREATE")
    public Response addSysUser(@RequestBody @Validated SysUserReq req) {
        String id = sysUserApplication.addSysUser(req, this.getUserId());
        return Response.ok(id);
    }

    /**
     * 修改系统用户
     *
     * @param req 用户请求类
     * @return Response
     */
    @PostMapping("/updateSysUser")
    @OperLog(operModule = "用户管理", operType = "UPDATE")
    public Response updateSysUser(@RequestBody @Validated SysUserUpdateReq req) {
        sysUserApplication.updateSysUser(req);
        return Response.ok();
    }

    /**
     * 根据id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @GetMapping("/selectSysUser")
    public Response<SysUserDto> selectSysUser(@RequestParam String userId) {
        SysUserDto sysUserDto = sysUserApplication.selectSysUser(userId);
        return Response.ok(sysUserDto);
    }

    /**
     * 删除系统用户
     *
     * @param userId 用户id
     * @return Response
     */
    @DeleteMapping("/deleteSysUser")
    @OperLog(operModule = "用户管理", operType = "DELETE")
    public Response deleteSysUser(@RequestParam String userId) {
        sysUserApplication.deleteSysUser(userId);
        return Response.ok();
    }

    /**
     * 重置密码
     *
     * @param userId 用户id
     * @return Response
     */
    @PutMapping("/passwordReset")
    @OperLog(operModule = "用户管理-重置密码", operType = "UPDATE")
    public Response passwordReset(@RequestParam String userId) {
        sysUserApplication.passwordReset(userId);
        return Response.ok();
    }

    /**
     * 修改密码
     *
     * @param req 修改密码请求类
     */
    @PostMapping("/changePassword")
    @OperLog(operModule = "用户管理-修改密码", operType = "UPDATE")
    public Response changePassword(@RequestBody @Validated ChangePasswordReq req) {
        sysUserApplication.changePassword(req.getUserId(), req.getOldPassword(), req.getNewPassword());
        return Response.ok();
    }

    /**
     * 根据id查询用户信息
     *
     * @param username 账号
     */
    @GetMapping("/loadUserByUsername")
    public Response<SysUser> loadUserByUsername(@RequestParam String username) {
        SysUser sysUserDto = sysUserApplication.loadUserByUsername(username);
        return Response.ok(sysUserDto);
    }


}
