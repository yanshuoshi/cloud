package com.yss.baseservice.domain.system;

import com.yss.baseservice.application.system.dto.OperLogDto;
import com.yss.baseservice.domain.system.entity.OperationLog;
import com.yss.baseservice.domain.system.mapper.OperationLogMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 操作日志领域层
 * @author lijianbin
 * @date 2021-09-18 11:19
 **/
@Service
public class OperationLogDomainService {
    private final OperationLogMapper operationLogMapper;

    public OperationLogDomainService(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    /**
     * 保存操作日志
     * @param module    模块
     * @param businessType  业务类型
     */
    public void saveOperationLog(String module, String businessType, String realName,Integer type,String userId) {
//        OperationLog operationLog = OperationLog.createOperationLog(module, businessType, realName,type,userId);
//        operationLog.insert();
    }

    /**
     * 查询操作日志
     * @param username 用户名
     * @param module    模块
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param rowBounds 分页
     * @return  操作日志
     */
    public List<OperLogDto> selectOperLogList(String module, String username, Date startDate, Date endDate, RowBounds rowBounds) {
        return operationLogMapper.selectOperLogList(module,username, startDate, endDate, rowBounds);
    }
}
