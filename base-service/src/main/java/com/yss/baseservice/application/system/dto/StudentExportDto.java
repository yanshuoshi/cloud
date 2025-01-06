package com.yss.baseservice.application.system.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yss.baseservice.infrastructure.common.ienum.SexEnum;
import lombok.Data;

/**
 * 导出学生信息
 * @author lijianbin
 * @date 2021-09-17 15:31
 **/
@Data
public class StudentExportDto {
    /** 登录名 */
    @Excel(name = "学号",width = 30)
    private String studentNumber;
    /** 姓名 */
    @Excel(name = "姓名",width = 30)
    private String studentName;
    /** 性别:F-女;M-男; */
    @Excel(name = "性别",width = 30)
    private SexEnum studentSex;
    // @Excel(name = "年龄",width = 30)
    // private Integer studentAge;
    /** 手机号 */
    @Excel(name = "手机号",width = 30)
    private String studentMobile;
    /** 身份证号码 */
    // @Excel(name = "身份证号码",width = 30)
    // private String identityCard;
    /** 班级 */
    @Excel(name = "班级",width = 30)
    private String organizationName;
    // @Excel(name = "专业",width = 30)
    // private String studentSpeciality;

    /** 性别枚举**/
    public String getStudentSex() {
        return studentSex.getValue();
    }
}
