package com.yss.baseservice.application.system;

import com.yss.baseservice.api.system.request.LoginLogAddReq;
import com.yss.baseservice.api.system.request.LoginLogReq;
import com.yss.baseservice.api.system.request.OperLogReq;
import com.yss.baseservice.application.system.dto.LoginLogDto;
import com.yss.baseservice.application.system.dto.OperLogDto;
import com.yss.baseservice.domain.system.LoginLogDomainService;
import com.yss.baseservice.domain.system.OperationLogDomainService;
import com.yss.baseservice.domain.system.entity.OperationLog;
import com.yss.baseservice.infrastructure.common.ienum.BusinessTypeEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 日志应用层
 *
 * @author lijianbin
 * @date 2021-09-18 10:37
 **/
@Component
@RequiredArgsConstructor
public class LogApplication {
    private final OperationLogDomainService operationLogDomainService;
    private final LoginLogDomainService loginLogDomainService;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 保存操作日志
     *
     * @param module       模块
     * @param businessType 业务类型
     */
    @Transactional(rollbackFor = Throwable.class)
    public void saveOperationLog(String module, String businessType, String realName, Integer type, String userId) {
        OperationLog operationLog = OperationLog.createOperationLog(module, businessType, realName, type, userId);
        elasticsearchRestTemplate.save(operationLog);
    }

    /**
     * 查询登录日志
     *
     * @param req 查询条件
     * @return 登录日志
     */
    public List<LoginLogDto> selectLoginLog(LoginLogReq req) {
        return loginLogDomainService.selectLoginLog(req.getUserName(), req.getStartDate(), req.getEndDate(),
                req.buildRowBounds());
    }

    /**
     * 查询操作日志
     *
     * @param req 查询条件
     * @return 操作日志
     */
    public List<OperLogDto> selectOperLogList(OperLogReq req) {

        // 创建 BoolQueryBuilder 用于组合多个查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 添加模糊查询条件
        if (StringUtils.isNotEmpty(req.getRealName()))
            boolQuery.must(QueryBuilders.matchQuery("realName", req.getRealName()));

        if (StringUtils.isNotEmpty(req.getModule()))
            boolQuery.must(QueryBuilders.matchQuery("module", req.getModule()));

        // 添加日期范围查询条件
        if (req.getStartDate() != null && req.getEndDate() != null) {
            boolQuery.filter(
                    QueryBuilders.rangeQuery("operationTime")
                            .gte(req.getStartDate().getTime())
                            .lte(req.getEndDate().getTime())
            );
        }

        // 设置分页参数
        int from = (req.getPageNum() - 1) * req.getPageSize();
        int size = req.getPageSize();
        //分页
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by(Sort.Order.asc("logId")));

        // 构建 Query 对象
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageRequest)
                .build();

        // 执行查询
        SearchHits<OperationLog> search = elasticsearchRestTemplate.search(query, OperationLog.class);
        // 处理查询结果
        List<OperLogDto> operLogDtos = new ArrayList<>();
        for (SearchHit<OperationLog> hit : search) {
            OperLogDto operLogDto = new OperLogDto(hit.getId(), hit.getContent().getRealName(),hit.getContent().getModule(),
                    hit.getContent().getOperationTime(),BusinessTypeEnum.getValue(hit.getContent().getBusinessType()),
                    hit.getContent().getUserId(),hit.getContent().getType());
            operLogDtos.add(operLogDto);
        }
        return operLogDtos;
    }

    /**
     * 添加日志
     */
    public void addLog(LoginLogAddReq req) {
        loginLogDomainService.saveLoginLog(req.getRealName(), req.getIpAddress(), req.getRemark(), req.getAddress(), req.getUserId(), req.getType());
    }
}
