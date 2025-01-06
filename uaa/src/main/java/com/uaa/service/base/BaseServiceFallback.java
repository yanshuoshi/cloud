package com.uaa.service.base;

import com.uaa.config.base.Response;
import com.uaa.entity.LoginLogAddReq;
import com.uaa.entity.RoleByUserIdDto;
import com.uaa.entity.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class BaseServiceFallback implements BaseService {

    // 只能将fallback抽离处理，否则会报错：Wrong state for SentinelResource annotation
    @Override
    public Response<SysUser> loadUserByUsername(@RequestParam String username) {
        // 实现方法的业务逻辑
        return Response.failed("获取用户信息失败,请稍等");
    }

    @Override
    public Response<List<RoleByUserIdDto>> getRoleByUserId(String userId) {
        return Response.failed("用户查询菜单权限失败,请稍等");
    }

    @Override
    public Response addLog(LoginLogAddReq req) {
        return Response.failed("添加日志失败,请稍等");
    }

}