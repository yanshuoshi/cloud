package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author lijianbin
 * @date 2021-09-18 11:40
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OperLogReq extends BasePageReq {
    /** 用户名 */
    private String realName;
    /** 模块 */
    private String module;
    /** 开始日期 */
    @Past(message = "开始日期不能大于当前日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /** 结束日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
