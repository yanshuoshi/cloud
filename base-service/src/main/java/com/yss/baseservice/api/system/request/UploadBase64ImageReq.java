package com.yss.baseservice.api.system.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author wangqi
 * @date 2021/12/3
 */
@Data
public class UploadBase64ImageReq {

    /**
     * 图片数据
     */
    @NotBlank(message = "图片数据不能为空")
    private String imageFile;


    /**
     * 文件名
     */
    private String fileName;
}
