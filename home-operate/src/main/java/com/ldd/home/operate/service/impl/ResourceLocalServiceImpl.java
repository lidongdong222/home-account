package com.ldd.home.operate.service.impl;

import com.ldd.home.operate.common.utils.AppUtil;
import com.ldd.home.operate.entity.ResourceBusi;
import com.ldd.home.operate.entity.ResourceInfo;
import com.ldd.home.operate.entity.req.ResourceBusiReq;
import com.ldd.home.operate.entity.resp.ResourceBusiResp;
import com.ldd.home.operate.mapper.ResourceBusiMapper;
import com.ldd.home.operate.mapper.ResourceInfoMapper;
import com.ldd.home.operate.service.IResourceLocalService;
import com.ldd.home.operate.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ResourceLocalServiceImpl  implements IResourceLocalService {

    public static final String APP_LOCAL_RESOURCE_DOWNLOAD_URL = "/common/resource/exportResource";

    @Autowired
    ResourceInfoMapper resourceInfoMapper;
    @Autowired
    ResourceBusiMapper resourceBusiMapper;

    @Autowired
    ISysConfigService configService;

    /**
     * 保存文件到本地文件夹
     *
     * @return
     */
    @Override
    public <T extends ResourceBusiReq> ResourceBusiResp saveFile(T t) throws IOException {
        Date now = new Date();
        String resInnerUrl = APP_LOCAL_RESOURCE_DOWNLOAD_URL; //内网访问ip
        String resInternetUrl = "";//外网访问ip
        //关联状态默认关联
        String relatedStatus = "1";
        long fileSize = t.getFileSize();
        //原文件名
        String originalFileName = t.getFileName();
        //系统唯一文件名
        String uniqueFileName = StringUtils.replace(UUID.randomUUID().toString(),"-","")
                +originalFileName.substring(originalFileName.lastIndexOf("."));
        OutputStream os = null;
        InputStream is = null;

        String resourceLocalPath = configService.getConfigValue("SYS_RESOURCE_LOCAL_PATH");
        try {
            File resourceLocalPathF = new File(resourceLocalPath);
            if(!resourceLocalPathF.exists()){
                resourceLocalPathF.mkdirs();
            }
            File localFile = new File(resourceLocalPath+File.separator+uniqueFileName);
            if(!localFile.exists()) localFile.createNewFile();
            if(t.getFile() instanceof File){
                os= new FileOutputStream(localFile);
                is =new FileInputStream((File) t.getFile());
                byte[] bytes = new byte[1024];
                int readlen = 0;
                while((readlen = is.read(bytes))!=-1){
                    os.write(bytes,0,readlen);
                }
                os.flush();
            }else if(t.getFile() instanceof MultipartFile){
                ((MultipartFile)t.getFile()).transferTo(localFile.toPath());
            }
        } catch (IOException e) {
            throw e;
        }finally {
            try {
                if(os!=null) os.close();
            } catch (IOException e) {}
            try {
                if(is!=null)is.close();
            } catch (IOException e) {}
        }

        ResourceInfo resourceInfo = ResourceInfo.builder()
                .resName(originalFileName)
                .resStoreType(ResourceInfo.ResStoreTypeEnum.LOCAL.getCode())
                .resUniqueName(uniqueFileName)
                .resSize(fileSize)
                .uploadTime(now)
                .resInternetUrl(resInternetUrl)
                .resServerIp(AppUtil.getCurrentIp()).resServerPath(resourceLocalPath).build();
        resourceInfoMapper.insertObtainId(resourceInfo);

        resInnerUrl = resInnerUrl+"?id="+resourceInfo.getResId();
        resourceInfoMapper.updateById(ResourceInfo.builder()
                .resId(resourceInfo.getResId())
                .resInnerUrl(resInnerUrl)
                .build());
        ResourceBusi resourceBusi = ResourceBusi.builder()
                .resId(resourceInfo.getResId())
                .busiId(t.getBusiId())
                .busiTable(t.getBusiTable())
                .status(relatedStatus).build();
        resourceBusiMapper.insert(resourceBusi);

        return ResourceBusiResp.builder().resId(resourceBusi.getResId())
                .busiId(resourceBusi.getBusiId())
                .resInnerUrl(resInnerUrl)
                .resInternetUrl(resInternetUrl)
                .status(true).build();
    }
}
