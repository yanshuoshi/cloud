package com.yss.baseservice.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yss.baseservice.application.system.dto.LoginLogDto;
import com.yss.baseservice.domain.system.entity.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lijianbin
 * @date 2021-09-18 13:44
 **/
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    /**
     * 查询登录日志
     * @param username  用户名
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param rowBounds 分页
     * @return  登录日志
     */
    List<LoginLogDto> selectLoginLog(@Param("username") String username, @Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate, RowBounds rowBounds);
}
