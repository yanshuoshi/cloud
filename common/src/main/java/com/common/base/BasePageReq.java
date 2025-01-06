package com.common.base;

import lombok.Data;
import org.apache.ibatis.session.RowBounds;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页请求
 * @author lijianbin
 * @date 2021-07-21 14:50
 **/
@Data
public abstract class BasePageReq {
    /** 页码，从1开始 */
    @Min(value = 1)
    private int pageNum = 1;
    /** 每页条数 */
    @Max(value = 2000)
    @Min(value = 1)
    private int pageSize = 10;

    public RowBounds buildRowBounds() {
        return new RowBounds((pageNum - 1) * pageSize, pageSize);
    }
}
