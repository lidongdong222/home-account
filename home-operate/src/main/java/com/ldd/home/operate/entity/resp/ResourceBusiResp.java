package com.ldd.home.operate.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceBusiResp {

//    来源业务id
    private String busiId;
//    资源id
    private Long resId;
//    内网url，在上传任何文件时都会生成，可直接在内网访问下载
    private String resInnerUrl;
//    资源互联网url，需要互联网时返回,提供互联网下载
    private String resInternetUrl;
//    保存成功状态
    private boolean status;


}
