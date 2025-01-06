package com.yss.baseservice.domain.system;

import com.alibaba.fastjson.JSON;
import com.common.config.SecurityUser;
import com.yss.baseservice.domain.system.entity.SysUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 多端登录
 * @author wangqi
 * @date 2022/3/24
 */
@Service
public class LoginMoreDomainService {

    private final SysUserDomainService sysUserDomainService;
    private final RoleDomainService roleDomainService;
    private final RedisTemplate<String, String> redisTemplate;

    public LoginMoreDomainService(SysUserDomainService sysUserDomainService, RoleDomainService roleDomainService, RedisTemplate<String, String> redisTemplate) {
        this.sysUserDomainService = sysUserDomainService;
        this.roleDomainService = roleDomainService;
        this.redisTemplate = redisTemplate;
    }

    public SecurityUser getSecurityUser(String s){
        SysUser sysUser = this.sysUserDomainService.selectUserByName(s);
        if (null== sysUser) {
            return new SecurityUser();
        }
        List<String> roleList = roleDomainService.selectRoleByUserId(sysUser.getUserId());
        SecurityUser securityUser = SecurityUser.loginUser(sysUser.getUserId(), sysUser.getUsername(), sysUser.getPassword(), roleList);
        redisTemplate.opsForValue().set(securityUser.getUsername(), JSON.toJSONString(securityUser), 24, TimeUnit.HOURS);
        return securityUser;
    }
}
