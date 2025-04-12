package com.ldd.home.operate.service;

import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.ResourceInfo;
import com.ldd.home.operate.entity.req.ResourceBusiReq;
import com.ldd.home.operate.entity.resp.ResourceBusiResp;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 文件资源处理接口
 */
public interface IResourceService {

    /**
     * 保存文件
     * @return
     */
    <T extends ResourceBusiReq> ResourceBusiResp saveFile(T t) throws IOException;

    /**
     * 保存文件列表
     * @return
     */
    <T extends ResourceBusiReq> List<ResourceBusiResp> saveFiles(List<T> list);

    /**
     * 获取文件
     * @return
     */
    File getFile(Long resId, String uniqueFileName) throws BusinessException, IOException;

}


