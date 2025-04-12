package com.ldd.home.operate.service.impl;

import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.ResourceInfo;
import com.ldd.home.operate.entity.req.ResourceBusiReq;
import com.ldd.home.operate.entity.resp.ResourceBusiResp;
import com.ldd.home.operate.mapper.ResourceInfoMapper;
import com.ldd.home.operate.service.IResourceLocalService;
import com.ldd.home.operate.service.IResourceService;
import com.ldd.home.operate.service.IServiceManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 文件资源服务
 */
@Service
@Slf4j
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    ResourceInfoMapper resourceInfoMapper;

    @Autowired
    IServiceManagerService serviceManagerService;

    @Autowired
    IResourceLocalService localService;

    /**
     * 保存文件
     * @return
     */
    @Override
    public <T extends ResourceBusiReq> ResourceBusiResp saveFile(T t) throws IOException {
        assert StrUtil.isEmpty(t.getBusiId())|| StrUtil.isEmpty(t.getBusiTable()) : "busiId、busiTable不能为空";
        /*
        获取资源信息有几种方式
        1.FTP、SFTP
        2.共享文件夹
        3.
         */
       return  localService.saveFile(t);
    }

    /**
     * 保存文件列表
     * @return
     */
    @Override
    public <T extends ResourceBusiReq> List<ResourceBusiResp> saveFiles(List<T> list) {
        return null;
    }

    /**
     * 获取文件
     */
    @Override
    public File getFile(Long resId, String uniqueFileName) throws BusinessException, IOException {
        File f = new File(uniqueFileName);
        if(!f.getParentFile().exists()) f.getParentFile().mkdirs();
        if(f.exists() && f.length()>1) throw new BusinessException("文件内容必须为空！");
        ResourceInfo resourceInfo = resourceInfoMapper.selectById(resId);
        File resourceFile = null;
        if(Objects.equals(resourceInfo.getResStoreType(),ResourceInfo.ResStoreTypeEnum.LOCAL.getCode())){
            resourceFile = new File(resourceInfo.getResServerPath()+File.separator+resourceInfo.getResUniqueName());
            if(!resourceFile.exists())throw new BusinessException("文件不存在！");
            FileUtils.copyFile(resourceFile,f);
        }
        return f;
    }

}
