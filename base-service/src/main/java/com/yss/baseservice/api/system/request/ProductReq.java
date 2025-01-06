package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 新增专业
 *
 * @author yss
 * @date 2022-4-11
 **/
@Data
public class ProductReq {

    /** 产品编号 */
    private String productCode;
    /** 产品名称 */
    @NotBlank(message = "产品名称不能为空")
    private String productName;
    /** 版本号 */
    private String version;
    /** 产品描述 */
    private String describe;
    /** 访问权限类型 1 公开 2 指定部门 3 指定人员 */
    // @NotBlank(message = "访问权限类型不能为空")
    private String permissionType;
    /** 专业id */
    @NotBlank(message = "专业id不能为空")
    private String majorId;
    /** 产品类型 1 软件 2 硬件 3 虚拟仿真 */
    @NotBlank(message = "产品类型编号不能为空")
    private String productType;
    /** 产品类型名称 */
    @NotBlank(message = "产品类型名称不能为空")
    private String productTypeName;
    /** 部门ids */
    private List<Integer> organizationIds;
    /** 人员ids */
    private List<String> userIds;
    /** svn地址 */
    // @NotBlank(message = "svn地址不能为空")
    private String svnUrl;
    /** 演示地址 */
    private String perform;

}
