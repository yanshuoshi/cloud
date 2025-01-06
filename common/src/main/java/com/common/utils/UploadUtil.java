package com.common.utils;

import com.common.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 上传工具类
 *
 * @author wangqi
 * @date 2021/11/1
 */
@Component
@Slf4j
public class UploadUtil {


    private static String FILE_PATH;
    private static String FILE_URL;

    private static String APK = ".apk";

    /**
     * 附件上传 返回文件名
     *
     * @param file 文件
     * @param url  传入的拼接地址
     */
    public static String uploadflie(MultipartFile file, String url) {
        String path;
        if (file.getSize() != 0) {
            //上传的多格式的视频文件-作为临时路径保存，转码以后删除-路径不能写//
            if (url == null) {
                path = FILE_PATH + "/";
            } else {
                path = FILE_PATH + "/" + url;
            }
            //截取字符串
            String mkdir = path.substring(0, path.lastIndexOf("/"));
            File tempFile = new File(mkdir);
            if (tempFile.exists()) {
                if (tempFile.isDirectory()) {
                    log.error("该文件夹存在。");
                } else {
                    log.error("同名的文件存在，不能创建文件夹。");
                }
            } else {
                tempFile.mkdirs();
            }
            //上传到本地磁盘/服务器
            try (OutputStream os = new FileOutputStream(new File(path));
                 InputStream is = file.getInputStream()) {
                log.info("写入本地磁盘/服务器");

                int len;
                byte[] buffer = new byte[2048];

                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return url;
    }

    /**
     * 上传apk
     *
     * @param file       文件
     * @param path       文件存储地址
     * @param uploadPath 下载地址文件路径
     * @return 下载地址
     */
    public static String uploadflie(MultipartFile file, String path, String uploadPath) {
        //判断文件夹是否存在，不存在创建文件夹
        File fileFolder = new File(path);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        String lastName = file.getOriginalFilename().substring(Objects.requireNonNull(file.getOriginalFilename()).indexOf("."));
        if (!APK.equals(lastName)) {
            throw new BaseException(500, "该文件不是apk文件");
        }
        //上传到本地磁盘/服务器
        try (OutputStream os = new FileOutputStream(new File(path + file.getOriginalFilename()));
             InputStream is = file.getInputStream()) {
            log.info("写入本地磁盘/服务器");

            int len;
            byte[] buffer = new byte[2048];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadPath + file.getOriginalFilename();
    }

    /**
     * 批量上传文件 （待测试）
     *
     * @param files 文件集合
     * @param url   传入的拼接路径
     */
    public static List<String> batchUpload(MultipartFile[] files, String url) {
        List<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            list.add(uploadflie(multipartFile, url));
        }
        return list;
    }

    /**
     * 删除文件
     */
    public static String delFile(String path) {
        String resultInfo;
        String filePath = FILE_PATH + path;
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                resultInfo = "1-删除成功";
            } else {
                resultInfo = "0-删除失败";
            }
        } else {
            resultInfo = "文件不存在！";
        }

        return resultInfo;
    }

    public static String uploadBase64Image(String imageFile, String url) {
        // 通过base64来转化图片
        imageFile = imageFile.replaceAll("data:image/jpeg;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] imageByte = null;
        try {
            imageByte = decoder.decodeBuffer(imageFile);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 生成文件名
        String path = FILE_PATH + "/" + url;
        try {
            // 生成文件
            File newimageFile = new File(path);
            newimageFile.createNewFile();
            if (!newimageFile.exists()) {
                newimageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(newimageFile);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    @Value("${file.path}")
    public void setPath(String path) {
        UploadUtil.FILE_PATH = path;
    }

    @Value("${file.url}")
    public void setUrl(String fileUrl) {
        UploadUtil.FILE_URL = fileUrl;
    }
}
