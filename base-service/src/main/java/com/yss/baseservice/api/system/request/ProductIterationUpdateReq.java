package com.yss.baseservice.api.system.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 编辑产品迭代信息
 *
 * @author yss
 * @date 2022-4-26
 **/
@Data
public class ProductIterationUpdateReq {

    /** 产品id */
    @NotNull(message = "产品id不能为空")
    private Integer productId;
    /** 迭代id */
    @NotNull(message = "迭代id不能为空")
    private Integer iterationId;
    /** 迭代描述 */
    private String description;
    /** 迭代时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

}
