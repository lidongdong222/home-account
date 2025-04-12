package com.ldd.home.operate.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtpInfoEntity {
//    内网IP
    private String serverInnerIp;
//    公网Ip
    private String serverInternetIp;
//    内网域名
    private String serverInnerDomain;
//    公网域名
    private String serverInternetDomain;
//    是否支持啥sftp上传
    private String ftpProtocol;
//    ftp端口
    private Integer ftpPort;
//    ftp用户名
    private String ftpUsername;
    //ftp密码
    private String ftpPassword;
    //sftp地址
    private String ftpServerPath;
    //文件上传后的前缀互联网url，拼接文件名则为文件互联网url，当仅获取需要互联网访问的文件时才会返回，否则不会返回
    //url末尾不带‘/’资源路径，请自行拼接
    private String resourceInnerPrefixNetUrl;
    //文件上传后的前缀互联网url，拼接文件名则为文件互联网url，当仅获取需要互联网访问的文件时才会返回，否则不会返回
    //url末尾不带‘/’资源路径，请自行拼接
    private String resourceInternetPrefixNetUrl;

    public enum FtpProtocolType{
        FTP("FTP"),
        SFTP("SFTP");

        String value;
        FtpProtocolType(String ftp) {
            this.value = ftp;
        }

        public String getValue(){
            return this.value;
        }
    }
}
