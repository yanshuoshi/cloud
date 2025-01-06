package com.yss.baseservice.api.system.request;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Excel用户导入
 * @author lijianbin
 * @date 2021-09-16 15:53
 **/
@Data
public class StudentImportReq {
    /** 登录名 */
    @NotBlank(message = "学号不能为空")
    @Excel(name = "学号")
    private String studentNumber;
    /** 姓名 */
    @NotBlank(message = "姓名不能为空")
    @Excel(name = "姓名")
    private String studentName;
    /** 性别:F-女;M-男; */
    @NotNull(message = "性别不能为空")
    @Excel(name = "性别")
    private String studentSex;
    // @Excel(name = "年龄")
    // private Integer studentAge;
    /** 身份证号码 */
    // @Excel(name = "身份证号码")
    // private String identityCard;
    /** 手机号 */
    @Excel(name = "手机号")
    private String studentMobile;
}
