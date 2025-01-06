package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 修改产品
 *
 * @author yss
 * @date 2022-4-11
 **/
@Data
public class ProductUpdateReq {

    /**
     * 产品id
     */
    @NotNull(message = "产品id不能为空")
    private Integer productId;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 版本号
     */
    private String version;
    /**
     * 产品描述
     */
    private String description;
    /**
     * 专业id
     */
    private String majorId;
    /** svn地址 */
    private String svnUrl;
    /** 演示地址及演示用户名密码 */
    private String perform;
    /**
     * 访问权限类型 1 公开 2 指定部门 3 指定人员
     */
    // @NotBlank(message = "访问权限类型不能为空")
    private String permissionType;
    /** 部门ids */
    private List<Integer> organizationIds;
    /** 人员ids */
    private List<String> userIds;
}
