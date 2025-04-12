package com.ldd.home.operate.entity.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ResourceMultipartFileBusiReq extends ResourceBusiReq{

    public ResourceMultipartFileBusiReq(MultipartFile file,String busiId,String busiTable,boolean needNet){
        super(busiId,busiTable,needNet);
        this.file = file;
    }


    //文件
    private MultipartFile file;


    @Override
    public String getFileName() {
        return file.getOriginalFilename();
    }

    @Override
    public Long getFileSize() {
        return file.getSize();
    }
}
