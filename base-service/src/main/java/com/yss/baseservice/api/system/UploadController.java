package com.yss.baseservice.api.system;

import com.common.base.BaseController;
import com.common.base.Response;
import com.common.utils.UploadUtil;
import com.yss.baseservice.api.system.request.UploadBase64ImageReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用上传接口
 *
 * @author wangqi
 * @date 2021/11/1
 */
@RestController
@RequestMapping("/system/upload/")
public class UploadController extends BaseController {

    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 文件名
     * @return
     */
    @PostMapping(value = "file")
    // @Async("taskExecutor1")
    public Response uploadFile(MultipartFile[] file, String fileName) {
        List<String> list = new ArrayList<>();
        /**
         * 为适用多文件上传，返回地址list集合
         */
        if (null != file && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                String name = file[i].getOriginalFilename();
                // assert name != null;
                // name = name.substring(name.indexOf("."));
                synchronized (this) {
                    String uploadFile = UploadUtil.uploadflie(file[i], StringUtils.isBlank(fileName) ? name : fileName);
                    list.add(uploadFile);
                }
            }
            return Response.ok(list);
        } else {
            return Response.error(500, "请选择图片或文件");
        }
    }

    /**
     * 上传base64的图片
     *
     * @param req 数据
     * @return
     */
    @PostMapping(value = "base64Image")
    public Response uploadBase64Image(@RequestBody UploadBase64ImageReq req) {
        String url = UploadUtil.uploadBase64Image(req.getImageFile(), StringUtils.isBlank(req.getFileName()) ? new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".png" : req.getFileName());
        return Response.ok(url);
    }

}
