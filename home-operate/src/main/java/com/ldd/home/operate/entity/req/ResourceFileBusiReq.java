package com.ldd.home.operate.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceFileBusiReq extends ResourceBusiReq{
    //文件
    private File file;

    public ResourceFileBusiReq(File file, String busiId, String busiTable, boolean needNet){
        super(busiId,busiTable,needNet);
        this.file = file;
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public Long getFileSize() {
        return file.length();
    }
}
