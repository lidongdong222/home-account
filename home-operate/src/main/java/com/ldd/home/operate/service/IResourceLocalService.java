package com.ldd.home.operate.service;

import com.ldd.home.operate.entity.req.ResourceBusiReq;
import com.ldd.home.operate.entity.resp.ResourceBusiResp;

import java.io.IOException;

public interface IResourceLocalService {
    public <T extends ResourceBusiReq> ResourceBusiResp saveFile(T t) throws IOException;
}
