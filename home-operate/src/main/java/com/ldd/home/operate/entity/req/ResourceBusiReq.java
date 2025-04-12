package com.ldd.home.operate.entity.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ResourceBusiReq {
    //来源业务id
    protected String busiId;
    //来源业务表
    protected String busiTable;
    //是否需要互联网
    protected boolean isNet = false;

    //返回类型为File类型或MultipartFile
    public abstract Object getFile();

    public abstract String getFileName();

    public abstract Long getFileSize();

}
