package com.yss.baseservice.api.system;

import com.common.base.Response;
import com.github.pagehelper.PageInfo;
import com.yss.baseservice.api.system.request.LoginLogAddReq;
import com.yss.baseservice.api.system.request.LoginLogReq;
import com.yss.baseservice.api.system.request.OperLogReq;
import com.yss.baseservice.api.system.request.OrganizationReq;
import com.yss.baseservice.application.system.LogApplication;
import com.yss.baseservice.application.system.dto.LoginLogDto;
import com.yss.baseservice.application.system.dto.OperLogDto;
import com.yss.baseservice.infrastructure.common.aspect.OperLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 日志管理
 * @author lijianbin
 * @date 2021-09-18 10:08
 **/
@RestController
@RequestMapping("/system")
public class LogController {
    private final LogApplication logApplication;

    public LogController(LogApplication logApplication) {
        this.logApplication = logApplication;
    }


    /**
     * 登录日志
     */
    @GetMapping("/logs/logList")
    public Response loginLog(@Validated LoginLogReq req){
        List<LoginLogDto> list = logApplication.selectLoginLog(req);
        return Response.ok(new PageInfo<>(list));
    }

    /**
     * 操作日志
     */
    @GetMapping("/modelLog/modelLogList")
    public Response operLog(@Validated OperLogReq req){
        List<OperLogDto> list = logApplication.selectOperLogList(req);
        return Response.ok(new PageInfo<>(list));
    }

    /**
     * 添加日志
     * @param req
     * @return
     */
    @PostMapping("addLog")
    public Response addLog(@RequestBody @Validated LoginLogAddReq req) {
        logApplication.addLog(req);
        return Response.ok();
    }
}
