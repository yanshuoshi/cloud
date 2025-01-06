package com.yss.baseservice.application.system;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.common.config.SecurityUser;
import com.common.utils.IpUtils;
import com.yss.baseservice.api.system.request.SysUserReq;
import com.yss.baseservice.api.system.request.SysUserUpdateReq;
import com.yss.baseservice.application.system.dto.SysUserDto;
import com.yss.baseservice.domain.system.LoginLogDomainService;
import com.yss.baseservice.domain.system.RoleDomainService;
import com.yss.baseservice.domain.system.SysUserDomainService;
import com.yss.baseservice.domain.system.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 系统用户应用层
 *
 * @author wangqi
 * @date 2021/11/1
 */
@Component
@Transactional(rollbackFor = Throwable.class)
public class SysUserApplication  {

    private final SysUserDomainService sysUserDomainService;
    private final RoleDomainService roleDomainService;
    private final LoginLogDomainService loginLogDomainService;
    private final RedisTemplate<String, String> redisTemplate;

    public SysUserApplication(SysUserDomainService sysUserDomainService, RoleDomainService roleDomainService, LoginLogDomainService loginLogDomainService, RedisTemplate<String, String> redisTemplate) {
        this.sysUserDomainService = sysUserDomainService;
        this.roleDomainService = roleDomainService;
        this.loginLogDomainService = loginLogDomainService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询系统用户列表
     *
     * @param username  用户名
     * @param mobile    联系方式
     * @param rowBounds 分页
     * @return 用户列表
     */
    public List<SysUserDto> selectSysUserList(String username, String realName, String mobile, String sex, Integer organizationId, RowBounds rowBounds) {
        return sysUserDomainService.selectSysUserList(username, realName, mobile, sex, organizationId, rowBounds);
    }

    /**
     * 添加系统用户
     *
     * @param sysUserReq 用户请求类
     * @param userId     当前用户id
     */
    public String addSysUser(SysUserReq sysUserReq, String userId) {
        return sysUserDomainService.saveUser(sysUserReq.getRealName(), sysUserReq.getUsername(), sysUserReq.getPassword(), sysUserReq.getSex(),
                sysUserReq.getMobile(), sysUserReq.getHeadurl(), sysUserReq.getOrganizationId(), userId );
    }

    /**
     * 修改系统用户
     *
     * @param sysUserUpdateReq 用户请求类
     */
    public void updateSysUser(SysUserUpdateReq sysUserUpdateReq) {
        // sysUserDomainService.updateUser(sysUserUpdateReq.getUserId(),sysUserUpdateReq.getRealName(),sysUserUpdateReq.getSex(),sysUserUpdateReq.getMobile(),
        //       sysUserUpdateReq.getHeadurl(),sysUserUpdateReq.getOrganizationId(),sysUserUpdateReq.getJobName(),sysUserUpdateReq.getHigherUserId());
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<SysUser>()
                .eq("user_id", sysUserUpdateReq.getUserId());
        if (StringUtils.isNotBlank(sysUserUpdateReq.getRealName())) {
            wrapper.set("real_name", sysUserUpdateReq.getRealName());
        }
        if (sysUserUpdateReq.getSex() != null) {
            wrapper.set("sex", sysUserUpdateReq.getSex());
        }
        if (StringUtils.isNotBlank(sysUserUpdateReq.getMobile())) {
            wrapper.set("mobile", sysUserUpdateReq.getMobile());
        }
        if (StringUtils.isNotBlank(sysUserUpdateReq.getHeadurl())) {
            wrapper.set("headurl", sysUserUpdateReq.getHeadurl());
        }
        if (sysUserUpdateReq.getOrganizationId() != null) {
            wrapper.set("organization_id", sysUserUpdateReq.getOrganizationId());
        }
        if (StringUtils.isNotBlank(sysUserUpdateReq.getMajorName())) {
            wrapper.set("major_name", sysUserUpdateReq.getMajorName());
        }
        wrapper.set("update_time",new Date());
        sysUserDomainService.update(wrapper);
    }

    /**
     * 删除系统用户
     *
     * @param userId 用户id
     */
    public void deleteSysUser(String userId) {
        sysUserDomainService.deleteUser(userId);
    }

    /**
     * 重置密码
     *
     * @param userId 用户id
     */
    public void passwordReset(String userId) {
        sysUserDomainService.passwordReset(userId);
    }

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    public void changePassword(String userId, String oldPassword, String newPassword) {
        sysUserDomainService.changePassword(userId, oldPassword, newPassword);
    }

    /**
     * 根据userId 查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    public SysUserDto selectSysUser(String userId) {
        return sysUserDomainService.selectSysUser(userId);
    }

    /**
     * username 查询用户信息
     *
     * @param username 用户id
     * @return 用户信息
     */
    public SysUserDto getUserByName(String username) {
        return this.sysUserDomainService.getUserByName(username);
    }


    /**
     * 登陆验证
     *
     * @param s 用户名
     */
    public SysUser loadUserByUsername(String s){
        return this.sysUserDomainService.selectUserByName(s);
    }
}
