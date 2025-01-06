package com.yss.work.domain.plan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.util.Date;

/**
 * 锻炼计划
 *
 * @author yss
 * @since 2024-04-26
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_plan")
public class TPlan extends Model<TPlan> {

    /** 标识id */
    @TableId
    private Integer planId;
    /** 计划名称 */
    private String planName;
    /** 锻炼周期开始日期 */
    private Date cycleStartTime;
    /** 锻炼周期结束日期 */
    private Date cycleEndTime;
    /** 锻炼开始时间 */
    private Date startTime;
    /** 锻炼结束时间 */
    private Date endTime;
    /** 是否启用 1 是 0 否 */
    private Integer isEnable;
    /** 是否删除 0 是 1 否 */
    private Integer status;
    /** 状态 1 已创建 2 进行中 3 已结束 */
    private Integer state;
    /** 创建人 */
    private String createUser;
    /** 创建时间 */
    private Date createTime;
    /** 1 无修改 2 修改 */
    private Integer type;
    /**
    * 新增
    * @param planName 计划名称
    * @param cycleStartTime 锻炼周期开始日期
    * @param cycleEndTime 锻炼周期结束日期
    * @param startTime 锻炼开始时间
    * @param endTime 锻炼结束时间
    * @param createUser 创建人
    */
    public static TPlan createTPlan(String planName,Date cycleStartTime,Date cycleEndTime,
                                    Date startTime,Date endTime,String createUser) {
            return TPlan.builder().planId((int) IdWorker.getId()).planName(planName).cycleStartTime(cycleStartTime).
                    cycleEndTime(cycleEndTime).startTime(startTime).endTime(endTime).isEnable(1).state(1).type(1).
                    status(1).createUser(createUser).createTime(new Date()).build();
    }



}

