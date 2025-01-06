package com.uaa.config.security.service;

import com.alibaba.fastjson.JSON;
import com.uaa.config.base.Response;
import com.uaa.entity.LoginLogAddReq;
import com.uaa.entity.RoleByUserIdDto;
import com.uaa.entity.SysUser;
import com.uaa.config.security.SecurityUser;
import com.uaa.service.base.BaseService;
import com.uaa.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService{

    private final BaseService baseService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Response<SysUser> user = baseService.loadUserByUsername(username);
        SysUser data = user.getData();
        if (user == null || data == null) {
            return null;
        }
        //获取权限
        Response<List<RoleByUserIdDto>> dtoResponse = baseService.getRoleByUserId(data.getUserId());
        List<String> authorities = null;
        List<RoleByUserIdDto> data1 = dtoResponse.getData();
        if(dtoResponse != null & CollectionUtils.isNotEmpty(data1)){
            authorities = data1.stream().map(RoleByUserIdDto::getRoleKey).collect(Collectors.toList());
        }
//        List<String> codes = role.stream().map(RoleByUserIdDto::getRoleKey).collect(Collectors.toList());
//        String[] authorities = null;
//        if (CollectionUtils.isNotEmpty(codes)) {
//            authorities = new String[codes.size()];
//            codes.toArray(authorities);
//        }
//        身份令牌
//        String principal = JSON.toJSONString(user);
        // 保存登录日志 TODO
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        baseService.addLog(new LoginLogAddReq(data.getRealName(),IpUtils.getV4IP(),"登录成功",IpUtils.getIpAddr(request),data.getUserId(),1));
        SecurityUser securityUser = SecurityUser.loginUser(data.getUserId(), data.getUsername(), data.getPassword(),null, authorities);
        redisTemplate.opsForValue().set(securityUser.getUsername(), JSON.toJSONString(securityUser), 24, TimeUnit.HOURS);
        return securityUser;
//        return User.withUsername(principal).password(user.getPassword()).authorities(authorities).accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false).build();
    }

}
