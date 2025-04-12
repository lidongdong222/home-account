package com.ldd.home.operate.service.impl;

import com.ldd.home.operate.common.entity.FtpInfoEntity;
import com.ldd.home.operate.common.utils.FtpUtil;
import com.ldd.home.operate.common.utils.SftpUtil;
import com.ldd.home.operate.entity.req.ResourceBusiReq;
import com.ldd.home.operate.service.IResourceFtpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class ResourceFtpServiceImpl implements IResourceFtpService {

    @Override
    public <T extends ResourceBusiReq> void saveFile(T t) {
        //原文件名
        String originalFileName = t.getFileName();
        //系统唯一文件名
        String uniqueFileName = StringUtils.replace(UUID.randomUUID().toString(),"-","")
                +originalFileName.substring(originalFileName.lastIndexOf("."));
        //暂时使用本地文件管理
        FtpInfoEntity ftpInfoEntity = new FtpInfoEntity();

        //内部资源地址
        String resInnerUrl = ftpInfoEntity.getResourceInnerPrefixNetUrl()+"/"+uniqueFileName;
        //互联网资源地址
        String resInternetUrl = t.isNet()?ftpInfoEntity.getResourceInternetPrefixNetUrl()+"/"+uniqueFileName:null;

        if(FtpInfoEntity.FtpProtocolType.FTP.getValue().equalsIgnoreCase(ftpInfoEntity.getFtpProtocol())){
            if(t.getFile() instanceof MultipartFile){
                FtpUtil.uploadFile(ftpInfoEntity,(MultipartFile)t.getFile(),uniqueFileName);
            }else if(t.getFile() instanceof File){
                FtpUtil.uploadFile(ftpInfoEntity,(File)t.getFile(),uniqueFileName);
            }
        }else if(FtpInfoEntity.FtpProtocolType.SFTP.getValue().equalsIgnoreCase(ftpInfoEntity.getFtpProtocol())){
            if(t.getFile() instanceof MultipartFile){
                SftpUtil.uploadFile(ftpInfoEntity,(MultipartFile)t.getFile(),uniqueFileName);
            }else if(t.getFile() instanceof File){
                SftpUtil.uploadFile(ftpInfoEntity,(File)t.getFile(),uniqueFileName);
            }
        }
    }
}
