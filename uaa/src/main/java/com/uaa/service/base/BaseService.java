package com.uaa.service.base;

import com.uaa.config.base.Response;
import com.uaa.config.feign.FeignConfig;
import com.uaa.entity.LoginLogAddReq;
import com.uaa.entity.RoleByUserIdDto;
import com.uaa.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * base-service服务调用接口
 */
@FeignClient(name = "base-service",
        fallback = BaseServiceFallback.class,
        configuration = FeignConfig.class)
public interface BaseService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/system/sysUser/loadUserByUsername")
    Response<SysUser> loadUserByUsername(@RequestParam("username") String username);

    /**
     * 用户登录查询权限
     *
     * @return
     */
    @GetMapping("/system/role/getRoleByUserId")
    Response<List<RoleByUserIdDto>> getRoleByUserId(@RequestParam("userId") String userId);

    /**
     * 添加日志
     *
     * @return
     */
    @PostMapping("/system/addLog")
    Response addLog(@RequestBody LoginLogAddReq req);
}
