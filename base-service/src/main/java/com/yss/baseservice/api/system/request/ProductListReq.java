package com.yss.baseservice.api.system.request;

import com.common.base.BasePageReq;
import lombok.Data;

/**
 * 产品列表
 *
 * @author yss
 * @date 2022-4-11
 **/
@Data
public class ProductListReq  extends BasePageReq {

    /**
     * 产品名称
     */
    private String productName;
    /**
     * 版本号
     */
    private String version;
    /**
     * 负责人
     */
    private String username;
    /**
     * 产品类型 1 软件 2 硬件 3 虚拟仿真
     */
    private String productType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 专业id
     */
    private String majorName;
    /** svn地址 */
    private String svnUrl;
    /** 演示地址及演示用户名密码 */
    private String perform;

}
