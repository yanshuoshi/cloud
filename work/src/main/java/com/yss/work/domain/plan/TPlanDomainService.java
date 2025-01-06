package com.yss.work.domain.plan;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yss.work.api.plan.request.TPlanListReq;
import com.yss.work.api.plan.request.TPlanReq;
import com.yss.work.application.plan.dto.TPlanUpdateListDto;
import com.yss.work.domain.plan.entity.TPlan;
import com.yss.work.domain.plan.mapper.TPlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 锻炼计划 领域层
 *
 * @author yss
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TPlanDomainService extends ServiceImpl<TPlanMapper, TPlan> {

    private final TPlanMapper tPlanMapper;

    /**
     * 添加
     *
     * @param req    请求类
     * @param userId 用户id
     */
    public Integer addTPlan(TPlanReq req, String userId) {
        TPlan tPlan = TPlan.createTPlan(req.getPlanName(), req.getCycleStartTime(), req.getCycleEndTime(), req.getStartTime(), req.getEndTime(),
                userId);
        tPlan.insert();
        return tPlan.getPlanId();
    }

    /**
     * 列表
     *
     * @param req 请求类
     */
    public List<TPlanUpdateListDto> selectTPlanList(TPlanListReq req) {
        return tPlanMapper.selectTPlanList(req);
    }
}
