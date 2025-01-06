package com.yss.baseservice.domain.other;

import com.yss.baseservice.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * school服务调用接口
 */
@FeignClient(name = "school-server",
        fallback = SchoolServiceFallback.class,
        configuration = FeignConfig.class)
public interface SchoolService {

    @GetMapping("/teacher/math/grade")
    public Object rs();
}
