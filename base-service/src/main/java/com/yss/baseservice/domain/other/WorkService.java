package com.yss.baseservice.domain.other;

import com.common.base.Response;
import com.common.base.ValidationGroup;
import com.yss.baseservice.domain.other.models.TPlanReq;
import com.yss.baseservice.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * school服务调用接口
 */
@FeignClient(name = "work-server",
        configuration = FeignConfig.class)
public interface WorkService {

    @PostMapping("/plan/add")
    Response saveTPlan(@RequestBody @Validated({ValidationGroup.Add.class}) TPlanReq req);
}
