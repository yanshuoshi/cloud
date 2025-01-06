package com.yss.baseservice.domain.other;

import org.springframework.stereotype.Component;

@Component
public class SchoolServiceFallback implements SchoolService {

    // 只能将fallback抽离处理，否则会报错：Wrong state for SentinelResource annotation
    @Override
    public String rs() {
        // 实现方法的业务逻辑
        return "Result from testB";
    }

}