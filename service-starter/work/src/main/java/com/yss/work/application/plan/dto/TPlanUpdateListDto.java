package com.yss.work.application.plan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 锻炼计划 - 修改
 *
 * @author yss
 * @since 2024-06-03
 */
@Data
public class TPlanUpdateListDto {

    /** 锻炼计划id */
    private Integer planId;
    /** 计划名称 */
    private String planName;
    /** 锻炼周期开始日期 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date cycleStartTime;
    /** 锻炼周期结束日期 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date cycleEndTime;
    /** 锻炼开始时间 */
    @JsonFormat(pattern="HH:mm",timezone = "GMT+8")
    private Date startTime;
    /** 锻炼结束时间 */
    @JsonFormat(pattern="HH:mm",timezone = "GMT+8")
    private Date endTime;
    /** 是否启用 1 是 0 否 */
    private Integer isEnable;
    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}

