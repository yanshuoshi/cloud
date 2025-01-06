package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.BaseException;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 系统用户
 *
 * @author wangqi
 * @date 2021/11/1
 */
@RestController
@RequestMapping("/system/sysUser")
public class SysUserController extends BaseController {

    private final SysUserApplication sysUserApplication;
    private final TokenStore tokenStore;
    private final RedisTemplate<String, String> redisTemplate;

    public SysUserController(SysUserApplication sysUserApplication, TokenStore tokenStore, RedisTemplate<String, String> redisTemplate) {
        this.sysUserApplication = sysUserApplication;
        this.tokenStore = tokenStore;
        this.redisTemplate = redisTemplate;
    }

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

    /**
     * 退出登录
     *
     * @param request
     */
    @GetMapping("/logout")
    public Response revokeToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader)) {
            throw new BaseException(500, "token不存在");
        }
        String tokenValue = authHeader.replace("Bearer", "").trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        tokenStore.removeAccessToken(accessToken);
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 清除 session
            session.invalidate();
        }
        // 清除认证状态
        SecurityContextHolder.clearContext();
        // jwt黑名单 因为jwt是无状态的，所以需要将token加入黑名单
        redisTemplate.opsForValue().set("jwtblacklist:" + tokenValue, tokenValue, 24, TimeUnit.HOURS);
        return Response.ok();
    }
}
