package com.yss.baseservice.api.system.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 新增产品迭代信息
 *
 * @author yss
 * @date 2022-4-11
 **/
@Data
public class ProductIterationReq {

    /** 产品id */
    @NotNull(message = "产品id不能为空")
    private Integer productId;
    /** 版本号 */
    @NotBlank(message = "版本号不能为空")
    private String version;
    /** 迭代内容 */
    @NotBlank(message = "迭代内容不能为空")
    private String description;
    /** 迭代时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

}
