package com.yss.baseservice.domain.other.models;

import com.common.base.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 锻炼计划
 *
 * @author yss
 * @since 2024-04-26
 */
@Data
@AllArgsConstructor
public class TPlanReq {

    /** 标识id */
    @NotNull(message = "标识id不能为空",groups = {ValidationGroup.Update.class})
    private Integer planId;
    /** 计划名称 */
    @NotBlank(message = "名称不能为空",groups = {ValidationGroup.Add.class})
    private String planName;
    /** 锻炼周期开始日期 */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date cycleStartTime;
    /** 锻炼周期结束日期 */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date cycleEndTime;
    /** 锻炼开始时间 */
    @JsonFormat(pattern="HH:mm",timezone = "GMT+8")
    private Date startTime;
    /** 锻炼结束时间 */
    @JsonFormat(pattern="HH:mm",timezone = "GMT+8")
    private Date endTime;
    /** 年级范围 */
    @NotNull(message = "年级范围不能为空",groups = {ValidationGroup.Add.class})
    private List<Integer> gradeIds;

}

