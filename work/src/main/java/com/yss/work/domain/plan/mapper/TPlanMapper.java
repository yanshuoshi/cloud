package com.yss.work.domain.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.work.api.plan.request.TPlanListReq;
import com.yss.work.application.plan.dto.TPlanUpdateListDto;
import com.yss.work.domain.plan.entity.TPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 锻炼计划 仓储层
 *
 * @author yss
 * @since 2024-04-26
 */
@Repository
public interface TPlanMapper extends BaseMapper<TPlan> {

    /**
     * 列表
     *
     * @param req 请求类
     */
    List<TPlanUpdateListDto> selectTPlanList(TPlanListReq req);
}
