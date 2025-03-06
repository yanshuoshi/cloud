package com.yss.work.application.plan;

import com.yss.work.api.plan.request.TPlanListReq;
import com.yss.work.api.plan.request.TPlanReq;
import com.yss.work.application.plan.dto.TPlanUpdateListDto;
import com.yss.work.domain.plan.TPlanDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 *
 * @author yss
 * @since 2024-04-26
 */
@Component
@RequiredArgsConstructor
public class TPlanApplication {

    private final TPlanDomainService tPlanDomainService;

    /**
     * 添加
     *
     * @param req 请求类
     */
    @Transactional(rollbackFor = Throwable.class)
    public void addTPlan(TPlanReq req, String userId) {
        tPlanDomainService.addTPlan(req, userId);
    }

    /**
     * 列表
     *
     * @param req 请求类
     */
    public List<TPlanUpdateListDto> selectTPlanList(TPlanListReq req) {
        return tPlanDomainService.selectTPlanList(req);
    }
}
