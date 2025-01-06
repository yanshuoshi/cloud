package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.OperLogDto;
import com.yss.baseservice.domain.system.entity.OperationLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 操作日志仓储层
 * @author lijianbin
 * @date 2021-09-18 11:23
 **/
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    /**
     * 查询操作日志
     * @param username  用户名
     * @param module    模块
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param rowBounds 分页
     * @return  操作日志
     */
    List<OperLogDto> selectOperLogList(@Param("module") String module, @Param("username") String username,
                                       @Param("startDate")Date startDate, @Param("endDate") Date endDate, RowBounds rowBounds);
}
