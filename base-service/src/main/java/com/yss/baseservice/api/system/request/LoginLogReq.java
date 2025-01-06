package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 登录日志列表
 * @author lijianbin
 * @date 2021-09-18 11:36
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogReq extends BasePageReq {
    /** 用户名 */
    private String userName;
    /** 开始日期 */
    @Past(message = "开始日期不能大于当前日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /** 结束日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
