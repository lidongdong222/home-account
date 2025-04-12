package com.ldd.home.operate.common.utils;

import com.ldd.home.operate.common.entity.FtpInfoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class SftpUtil {
    /**
     * 上传文件到服务器
     * @param ftpInfoEntity
     * @param file
     * @param uniqueFileName
     */
    public static boolean uploadFile(FtpInfoEntity ftpInfoEntity, MultipartFile file, String uniqueFileName) {
        return true;
    }

    /**
     * 上传文件到服务器
     * @param ftpInfoEntity
     * @param file
     * @param uniqueFileName
     */
    public static boolean uploadFile(FtpInfoEntity ftpInfoEntity, File file, String uniqueFileName) {
        return true;
    }
}
